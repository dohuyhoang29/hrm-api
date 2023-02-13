package com.aptech.hrmapi.mapper;

import com.aptech.hrmapi.dto.RoleDto;
import com.aptech.hrmapi.model.role.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    List<RoleDto> toDtos(List<Role> entities);
    List<Role> toEntities(List<RoleDto> dtos);
    RoleDto toDto(Role entity);
    Role toEntity(RoleDto dto);
}
