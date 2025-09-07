// LoginRequest.java
package org.library.controller;

import java.io.Serializable;

/**
 * 这个LoginRequest类用于封装用户登录请求的数据，包含以下特点：
 * 序列化支持: 实现了Serializable接口，支持网络传输和持久化
 * 基本属性: 包含用户名和密码两个核心属性
 * 构造函数: 提供默认构造函数和带参数的构造函数
 * 访问方法: 提供标准的getter和setter方法
 * 字符串表示: 重写toString()方法，便于调试和日志记录
 * 该类将被用于AuthController中的login方法，作为HTTP请求体的数据传输对象(DTO)。
 */
public class LoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    
    // 默认构造函数
    public LoginRequest() {
    }
    
    // 带参数的构造函数
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getter和Setter方法
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
