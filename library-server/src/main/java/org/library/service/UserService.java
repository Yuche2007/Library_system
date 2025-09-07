package org.library.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.library.entity.Role;
import org.library.entity.User;
import org.library.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// UserService.java
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    //@Override
    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        List<Role> roles = roleService.findRolesByUserId(user.getId());
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    public User saveUser(User user) {
        boolean bln = save(user);
        if (bln) {
            return user;
        } else {
            return null;
        }
    }
}