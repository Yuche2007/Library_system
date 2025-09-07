// RoleRepository.java
package org.library.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.library.entity.Permission;
import org.library.entity.Role;
import org.library.mapper.RoleMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    private final RoleMapper roleMapper;
    
    public RoleService(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }
    
    public List<Permission> findPermissionsByRoleId(Long roleId) {
        return roleMapper.selectPermissionsByRoleId(roleId);
    }
    
    public List<Role> findRolesByUserId(Long userId) {
        return roleMapper.selectRolesByUserId(userId);
    }
}
