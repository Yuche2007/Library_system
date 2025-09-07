<!-- BookList.vue -->
<template>
  <div class="book-list">
    <el-card>
      <div slot="header">
        <span>图书列表</span>
        <el-input
          v-model="searchKeyword"
          placeholder="搜索图书"
          style="width: 300px; margin-left: 20px;"
          @keyup.enter="searchBooks"
        >
          <el-button slot="append" icon="el-icon-search" @click="searchBooks"></el-button>
        </el-input>
        <el-button v-if="hasRole('ADMIN')" type="primary" @click="showAddDialog" style="float: right;">
          添加图书
        </el-button>
      </div>
      
      <el-table :data="books" style="width: 100%">
        <el-table-column prop="title" label="书名" width="200"></el-table-column>
        <el-table-column prop="author" label="作者" width="150"></el-table-column>
        <el-table-column prop="isbn" label="ISBN" width="150"></el-table-column>
        <el-table-column prop="publisher" label="出版社" width="150"></el-table-column>
        <el-table-column prop="availableCopies" label="可借数量" width="100"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="mini" @click="viewBook(scope.row)">查看</el-button>
            <el-button v-if="hasRole('ADMIN')" size="mini" @click="editBook(scope.row)">编辑</el-button>
            <el-button v-if="hasRole('ADMIN')" size="mini" type="danger" @click="deleteBook(scope.row)">删除</el-button>
            <el-button v-if="hasRole('USER')" size="mini" type="primary" @click="borrowBook(scope.row)">借阅</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <el-pagination
        @current-change="handlePageChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        style="margin-top: 20px; text-align: center;">
      </el-pagination>
    </el-card>
    
    <!-- 添加/编辑图书对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="500px">
      <el-form :model="currentBook" :rules="bookRules" ref="bookForm">
        <el-form-item label="书名" prop="title">
          <el-input v-model="currentBook.title"></el-input>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="currentBook.author"></el-input>
        </el-form-item>
        <el-form-item label="ISBN" prop="isbn">
          <el-input v-model="currentBook.isbn"></el-input>
        </el-form-item>
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="currentBook.publisher"></el-input>
        </el-form-item>
        <el-form-item label="总数量" prop="totalCopies">
          <el-input-number v-model="currentBook.totalCopies" :min="0"></el-input-number>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="currentBook.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveBook">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'BookList',
  data() {
    return {
      books: [],
      searchKeyword: '',
      currentPage: 1,
      pageSize: 10,
      total: 0,
      dialogVisible: false,
      dialogTitle: '',
      currentBook: {
        id: null,
        title: '',
        author: '',
        isbn: '',
        publisher: '',
        totalCopies: 0,
        availableCopies: 0,
        description: ''
      },
      bookRules: {
        title: [{ required: true, message: '请输入书名', trigger: 'blur' }],
        author: [{ required: true, message: '请输入作者', trigger: 'blur' }]
      }
    }
  },
  mounted() {
    this.loadBooks()
  },
  methods: {
    loadBooks() {
      this.$http.get(`/api/books?page=${this.currentPage - 1}&size=${this.pageSize}&keyword=${this.searchKeyword}`)
        .then(response => {
          this.books = response.data
          // 这里应该从后端获取总数
          this.total = this.books.length
        })
    },
    searchBooks() {
      this.currentPage = 1
      this.loadBooks()
    },
    handlePageChange(page) {
      this.currentPage = page
      this.loadBooks()
    },
    showAddDialog() {
      this.dialogTitle = '添加图书'
      this.currentBook = {
        id: null,
        title: '',
        author: '',
        isbn: '',
        publisher: '',
        totalCopies: 0,
        availableCopies: 0,
        description: ''
      }
      this.dialogVisible = true
    },
    editBook(book) {
      this.dialogTitle = '编辑图书'
      this.currentBook = { ...book }
      this.dialogVisible = true
    },
    saveBook() {
      this.$refs.bookForm.validate((valid) => {
        if (valid) {
          const url = this.currentBook.id ? `/api/books/${this.currentBook.id}` : '/api/books'
          const method = this.currentBook.id ? 'put' : 'post'
          
          this.$http[method](url, this.currentBook)
            .then(() => {
              ElMessage.success('保存成功')
              this.dialogVisible = false
              this.loadBooks()
            })
            .catch(error => {
              ElMessage.error('保存失败: ' + error.message)
            })
        }
      })
    },
    deleteBook(book) {
      ElMessageBox.confirm('确定要删除这本书吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http.delete(`/api/books/${book.id}`)
          .then(() => {
            ElMessage.success('删除成功')
            this.loadBooks()
          })
          .catch(error => {
            ElMessage.error('删除失败: ' + error.message)
          })
      })
    },
    viewBook(book) {
      // 查看图书详情
      this.$router.push(`/books/${book.id}`)
    },
    borrowBook(book) {
      // 借阅图书
      const userId = this.$store.state.user.id // 假设从store获取用户ID
      this.$http.post('/api/borrow', { userId, bookId: book.id })
        .then(() => {
          ElMessage.success('借阅成功')
          this.loadBooks()
        })
        .catch(error => {
          ElMessage.error('借阅失败: ' + error.message)
        })
    },
    hasRole(role) {
      // 检查用户角色
      return this.$store.state.user.roles.includes(role)
    }
  }
}
</script>
