package com.aptech.hrmapi.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ActionDTO {
    private Long id;
    @NotBlank(message = "Action Code không được để trống")
    @Pattern(regexp = "^[A-z0-9]*$")
    private String actionCode;
    @NotBlank(message = "Action Name không được để trống")
    private String actionName;
    private String description;
    private Integer status;
}
