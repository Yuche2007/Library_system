package org.library.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.library.entity.Book;
import org.library.mapper.BookMapper;


import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service
public class BookService extends ServiceImpl<BookMapper, Book>  {
    private final BookMapper bookMapper;

    public BookService(BookMapper bookRepository) {
        this.bookMapper = bookRepository;
    }

    public IPage<Book> getAllBooks(String keyword, int page, int size) {
        Page<Book> pageable = new Page<>(page, size);
        if (keyword != null && !keyword.isEmpty()) {
            return bookMapper.findByTitleContainingOrAuthorContaining(keyword, pageable);
        }
        return bookMapper.selectByCategory(pageable, keyword);
    }

    public Book getBookById(Long id) {
        return getById(id);
    }

    public Book saveBook(Book book) {
        boolean bln = super.save(book);
        return bln? book : null;
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        if (existingBook != null) {
            book.setId(id);
            //int std = bookMapper.updateById(book);
            return saveOrUpdate(book) ? book : null;
        }
        return null;
    }

    public boolean deleteBook(Long id) {
        return removeById(id);
    }

    // 新增方法：根据标题或作者查询
    public IPage<Book> searchBooksByTitleOrAuthor(String title, String author, int page, int size) {
        Page<Book> pageable = new Page<>(page, size);
        return bookMapper.findByTitleContainingOrAuthorContaining(title, author, pageable);
    }
}
