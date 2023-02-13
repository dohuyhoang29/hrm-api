package com.aptech.hrmapi.service;

import com.aptech.hrmapi.model.User;

public interface UserService {
    User getByUsername(String username);
}
