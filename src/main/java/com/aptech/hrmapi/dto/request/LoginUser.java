package com.aptech.hrmapi.dto.request;

import lombok.Data;

@Data
public class LoginUser {
    private String username;
    private String password;
    public LoginUser(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
