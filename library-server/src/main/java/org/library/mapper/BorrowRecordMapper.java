// BorrowRecordMapper.java
package org.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.library.entity.BorrowRecord;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BorrowRecordMapper extends BaseMapper<BorrowRecord> {
    
    @Select("SELECT br.* FROM borrow_records br WHERE br.user_id = #{userId}")
    List<BorrowRecord> selectByUserId(@Param("userId") Long userId);
    
    @Select("SELECT br.* FROM borrow_records br " +
            "JOIN books b ON br.book_id = b.id " +
            "WHERE br.user_id = #{userId} AND b.title LIKE CONCAT('%', #{title}, '%')")
    List<BorrowRecord> selectByUserIdAndBookTitle(@Param("userId") Long userId, @Param("title") String title);
    
    @Select("SELECT br.* FROM borrow_records br WHERE br.book_id = #{bookId} AND br.status = 'BORROWED'")
    List<BorrowRecord> selectActiveByBookId(@Param("bookId") Long bookId);
    
    @Select("SELECT br.* FROM borrow_records br WHERE br.due_date < #{date} AND br.status = 'BORROWED'")
    List<BorrowRecord> selectOverdueRecords(@Param("date") LocalDate date);
    
    @Update("UPDATE borrow_records SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
