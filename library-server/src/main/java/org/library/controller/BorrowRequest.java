// BorrowRequest.java
package org.library.controller;

import java.io.Serializable;

public class BorrowRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long userId;
    private Long bookId;
    
    // 默认构造函数
    public BorrowRequest() {
    }
    
    // 带参数的构造函数
    public BorrowRequest(Long userId, Long bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
    
    // Getter和Setter方法
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getBookId() {
        return bookId;
    }
    
    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
    
    @Override
    public String toString() {
        return "BorrowRequest{" +
                "userId=" + userId +
                ", bookId=" + bookId +
                '}';
    }
}
