package com.aptech.hrmapi.mapper;

import com.aptech.hrmapi.dto.UserDTO;
import com.aptech.hrmapi.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    List<UserDTO> toDtos(List<User> entities);
    List<User> toEntities(List<UserDTO> dtos);
    UserDTO toDto(User entity);
    User toEntity(UserDTO dto);
}
