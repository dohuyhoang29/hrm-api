package com.aptech.hrmapi.mapper;

import com.aptech.hrmapi.dto.RoleDTO;
import com.aptech.hrmapi.model.role.Role;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    List<RoleDTO> toDtos(List<Role> entities);
    List<Role> toEntities(List<RoleDTO> dtos);
    RoleDTO toDto(Role entity);
    Role toEntity(RoleDTO dto);
}
