package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.repository.UserRepository;
import com.aptech.hrmapi.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
