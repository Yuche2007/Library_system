// PermissionRepository.java
package org.library.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.library.entity.Permission;
import org.library.mapper.PermissionMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {
    private final PermissionMapper permissionMapper;
    
    public PermissionService(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }
    
    public List<Permission> findByRoleName(String roleName) {
        return permissionMapper.selectByRoleName(roleName);
    }
}
