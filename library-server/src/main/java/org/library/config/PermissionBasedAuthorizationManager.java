// PermissionBasedAuthorizationManager.java
package org.library.config;

import lombok.var;
import org.library.service.PermissionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class PermissionBasedAuthorizationManager {
    private final PermissionService permissionRepository;

    public PermissionBasedAuthorizationManager(PermissionService permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public boolean hasPermission(Authentication authentication, String resource, String action) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String roleName = authority.getAuthority();
            if (roleName.startsWith("ROLE_")) {
                roleName = roleName.substring(5); // 移除"ROLE_"前缀

                // 超级管理员拥有所有权限
                if ("SUPER_ADMIN".equals(roleName)) {
                    return true;
                }

                // 检查角色是否具有指定资源和操作的权限
                var permissions = permissionRepository.findByRoleName(roleName);
                for (var permission : permissions) {
                    if (resource.equals(permission.getResource()) && action.equals(permission.getAction())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}