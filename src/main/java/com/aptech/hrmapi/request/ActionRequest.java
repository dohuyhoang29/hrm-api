package com.aptech.hrmapi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActionRequest extends PageOption{
    private String actionName;
    private String actionCode;
}
