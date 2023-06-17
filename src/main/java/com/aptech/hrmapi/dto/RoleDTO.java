package com.aptech.hrmapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Long id;
    @NotBlank(message = "Role Code không được để trống")
    @Pattern(regexp = "^[A-z0-9]*$", message = "Role Code không được chứa ký tự đặc biệt")
    private String roleCode;
    @NotBlank(message = "Role Name không được để trống")
    private String roleName;
    private Integer status;
}
