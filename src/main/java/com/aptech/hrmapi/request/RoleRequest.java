package com.aptech.hrmapi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleRequest extends PageOption{
    private String roleCode;
    private String roleName;
}
