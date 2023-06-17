package com.aptech.hrmapi.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class FeatureDTO {
    private Long id;
    @NotBlank(message = "Feature Code không được để trống")
    private String featureCode;
    @NotBlank(message = "Feature Name không được để trống")
    private String featureName;
    @NotBlank(message = "Feature URL không được để trống")
    private String featureURL;
    private String description;
    private Integer status;
}
