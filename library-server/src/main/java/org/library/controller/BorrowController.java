package org.library.controller;

import org.library.entity.BorrowRecord;
import org.library.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// BorrowController.java
@RestController
@RequestMapping("/api/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BorrowRecord> borrowBook(@RequestBody BorrowRequest borrowRequest) {
        BorrowRecord record = borrowService.borrowBook(borrowRequest.getUserId(), borrowRequest.getBookId());
        return ResponseEntity.ok(record);
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BorrowRecord> returnBook(@PathVariable Long id) {
        BorrowRecord record = borrowService.returnBook(id);
        return ResponseEntity.ok(record);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BorrowRecord>> getUserBorrows(@PathVariable Long userId) {
        List<BorrowRecord> records = borrowService.getUserBorrows(userId);
        return ResponseEntity.ok(records);
    }
}
