// UserMapper.java
package org.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.library.entity.Role;
import org.library.entity.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    @Select("SELECT r.* FROM roles r " +
            "JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<Role> selectRolesByUserId(@Param("userId") Long userId);
    
    @Select("SELECT u.* FROM users u WHERE u.username = #{username}")
    User selectByUsername(@Param("username") String username);
    
    @Select("SELECT u.* FROM users u WHERE u.email = #{email}")
    User selectByEmail(@Param("email") String email);
    
    @Insert("INSERT INTO user_roles(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
    
    @Delete("DELETE FROM user_roles WHERE user_id = #{userId}")
    int deleteUserRolesByUserId(@Param("userId") Long userId);
}
