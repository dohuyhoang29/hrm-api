package com.aptech.hrmapi.service;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.UserDTO;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.request.UserRequest;
import com.aptech.hrmapi.response.PageResponse;

public interface UserService {
    User getByUsername(String username);
    PageResponse<UserDTO> getAllUser(UserRequest request);
    UserDTO getUser(Long id);
    UserDTO saveOrUpdate(UserDTO dto, AddOrUpdateType type);
    void deleteUser(Long id);
}
