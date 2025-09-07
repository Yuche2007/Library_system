package org.library.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.library.entity.Book;
import org.library.entity.BorrowRecord;
import org.library.mapper.BorrowRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

// BorrowService.java
@Service
public class BorrowService extends ServiceImpl<BorrowRecordMapper, BorrowRecord> {
    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Autowired
    private BookService bookService;

    @Transactional
    public BorrowRecord borrowBook(Long userId, Long bookId) {
        Book book = bookService.getBookById(bookId);
                //.orElseThrow(() -> new RuntimeException("图书不存在"));
        if(book == null){
            throw new RuntimeException("图书不存在");
        }
        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("图书库存不足");
        }

        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(30)); // 借阅30天

       save(record);

        // 更新图书库存
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookService.save(book);

        return record;
    }

    @Transactional
    public BorrowRecord returnBook(Long id) {
        BorrowRecord record = this.getById(id);
                //.orElseThrow(() -> new RuntimeException("借阅记录不存在"));
        if(record == null){
            throw new RuntimeException("借阅记录不存在");
        }
        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowRecord.BorrowStatus.RETURNED);

        save(record);

        // 更新图书库存
        Book book = bookService.getById(record.getBookId());//.orElseThrow();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookService.save(book);

        return record;
    }

    public List<BorrowRecord> getUserBorrows(Long userId) {
        return borrowRecordMapper.selectByUserId(userId);
    }
}
