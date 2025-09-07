// RegisterRequest.java
package org.library.controller;

import java.io.Serializable;

/**
 * 这个RegisterRequest类用于封装用户注册请求的数据，包含以下特点：
 * 序列化支持: 实现了Serializable接口，支持网络传输和持久化
 * 基本属性: 包含用户名、密码、邮箱、全名和电话等用户注册所需的核心信息
 * 构造函数: 提供默认构造函数和带参数的构造函数
 * 访问方法: 提供标准的getter和setter方法
 * 字符串表示: 重写toString()方法，便于调试和日志记录
 * 该类将被用于AuthController中的register方法，作为HTTP请求体的数据传输对象(DTO)。
 */
public class RegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    
    // 默认构造函数
    public RegisterRequest() {
    }
    
    // 带参数的构造函数
    public RegisterRequest(String username, String password, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
