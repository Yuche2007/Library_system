
// JwtResponse.java
package org.library.controller;

import java.io.Serializable;

public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    
    private final String jwtToken;
    
    // 构造函数
    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
    
    // Getter方法
    public String getToken() {
        return this.jwtToken;
    }
    
    @Override
    public String toString() {
        return "JwtResponse{" +
                "jwtToken='" + jwtToken + '\'' +
                '}';
    }
}
