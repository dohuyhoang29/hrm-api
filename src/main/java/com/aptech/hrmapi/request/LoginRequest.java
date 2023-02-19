package com.aptech.hrmapi.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class LoginRequest {
    @NotEmpty(message = "Username không được để trống")
    @NotNull(message = "Username không được để trống")
    private String username;
    @NotEmpty(message = "Password không được để trống")
    @NotNull(message = "Password không được để trống")
    private String password;
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
