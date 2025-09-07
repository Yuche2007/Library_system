// RoleMapper.java
package org.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.library.entity.Permission;
import org.library.entity.Role;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    
    @Select("SELECT p.* FROM permissions p " +
            "JOIN role_permissions rp ON p.id = rp.permission_id " +
            "WHERE rp.role_id = #{roleId}")
    List<Permission> selectPermissionsByRoleId(@Param("roleId") Long roleId);
    
    @Select("SELECT r.* FROM roles r " +
            "JOIN user_roles ur ON r.id = ur.role_id " +
            "WHERE ur.user_id = #{userId}")
    List<Role> selectRolesByUserId(@Param("userId") Long userId);
    
    @Select("SELECT r.* FROM roles r WHERE r.name = #{name}")
    Role selectByName(@Param("name") String name);
    
    @Insert("INSERT INTO role_permissions(role_id, permission_id) VALUES(#{roleId}, #{permissionId})")
    int insertRolePermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    
    @Delete("DELETE FROM role_permissions WHERE role_id = #{roleId}")
    int deleteRolePermissionsByRoleId(@Param("roleId") Long roleId);
}
