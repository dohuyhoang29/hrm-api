package com.aptech.hrmapi.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserRequest extends PageOption{
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
}
