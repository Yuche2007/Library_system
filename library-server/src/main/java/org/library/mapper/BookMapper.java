// BookMapper.java
package org.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;
import org.library.entity.Book;

/**
 * 这些Mapper接口提供了以下功能：
 * UserMapper:
 * 基本的用户CRUD操作
 * 查询用户的角色信息
 * 根据用户名或邮箱查找用户
 * 管理用户角色关联关系
 * RoleMapper:
 * 基本的角色CRUD操作
 * 查询角色的权限信息
 * 查询用户的角色信息
 * 根据角色名查找角色
 * 管理角色权限关联关系
 * PermissionMapper:
 * 基本的权限CRUD操作
 * 根据角色名查询权限
 * 根据权限名查找权限
 * 根据资源和操作查找权限
 * BookMapper:
 * 基本的图书CRUD操作
 * 分页查询图书（支持关键词搜索）
 * 根据ISBN查找图书
 * 按分类查询图书
 * 更新图书可借数量
 * BorrowRecordMapper:
 * 基本的借阅记录CRUD操作
 * 查询用户的借阅记录
 * 查询用户特定图书的借阅记录
 * 查询图书的活跃借阅记录
 * 查询过期未还的记录
 * 更新借阅状态
 * 所有Mapper都继承了MyBatis-Plus的BaseMapper，获得了通用的增删改查能力，同时通过MyBatis注解定义了特定的查询方法，满足系统各种业务需求。
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

    @Select("SELECT b.* FROM books b " +
            "WHERE b.title LIKE CONCAT('%', #{keyword}, '%') " +
            "OR b.author LIKE CONCAT('%', #{keyword}, '%') " +
            "OR b.isbn LIKE CONCAT('%', #{keyword}, '%')")
    IPage<Book> selectBookPage(Page<Book> page, @Param("keyword") String keyword);

    @Select("SELECT b.* FROM books b WHERE b.isbn = #{isbn}")
    Book selectByIsbn(@Param("isbn") String isbn);

    @Select("SELECT b.* FROM books b WHERE b.category = #{category}")
    IPage<Book> selectByCategory(Page<Book> page, @Param("category") String category);

    @Update("UPDATE books SET available_copies = available_copies - 1 WHERE id = #{id} AND available_copies > 0")
    int decreaseAvailableCopies(@Param("id") Long id);

    @Update("UPDATE books SET available_copies = available_copies + 1 WHERE id = #{id}")
    int increaseAvailableCopies(@Param("id") Long id);

    // 新增方法：根据标题或作者模糊查询
    @Select("SELECT * FROM books WHERE title LIKE CONCAT('%', #{title}, '%') OR author LIKE CONCAT('%', #{author}, '%')")
    IPage<Book> selectByTitleContainingOrAuthorContaining(
            Page<Book> page,
            @Param("title") String title,
            @Param("author") String author);

    IPage<Book> findByTitleContainingOrAuthorContaining(String keyword,Page<Book> pageable);

    IPage<Book> findByTitleContainingOrAuthorContaining(String keyword,String author, Page<Book> pageable);
}
