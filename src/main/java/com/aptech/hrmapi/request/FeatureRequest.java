package com.aptech.hrmapi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FeatureRequest extends PageOption{
    private String featureCode;
    private String featureName;
    private String featureURL;
    private String description;
}
