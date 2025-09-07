// PermissionMapper.java
package org.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.library.entity.Permission;

import java.util.List;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    
    @Select("SELECT p.* FROM permissions p " +
            "JOIN role_permissions rp ON p.id = rp.permission_id " +
            "JOIN roles r ON rp.role_id = r.id " +
            "WHERE r.name = #{roleName}")
    List<Permission> selectByRoleName(@Param("roleName") String roleName);
    
    @Select("SELECT p.* FROM permissions p WHERE p.name = #{name}")
    Permission selectByName(@Param("name") String name);
    
    @Select("SELECT p.* FROM permissions p WHERE p.resource = #{resource} AND p.action = #{action}")
    Permission selectByResourceAndAction(@Param("resource") String resource, @Param("action") String action);
}
