package com.aptech.hrmapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserDTO {
    private Long id;
    @NotBlank(message = "Username không được để trống")
    @Pattern(regexp = "^[A-z0-9]*$", message = "Usernmae không được chứa ký tự đặc biệt")
    private String username;
    @NotBlank(message = "Full name không được để trống")
    private String fullName;
    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]+(\\.[_a-zA-Z0-9]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,15})$", message = "Email không đúng định dạng")
    private String email;
    @NotBlank(message = "Phone number không được để trống")
    @Pattern(regexp = "^((0|84)?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$", message = "Phone number không đúng đinh dạng")
    private String phoneNumber;
    private String avatar;
}
