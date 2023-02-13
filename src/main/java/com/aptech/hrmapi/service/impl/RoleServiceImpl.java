package com.aptech.hrmapi.service.impl;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.RoleDto;
import com.aptech.hrmapi.mapper.RoleMapper;
import com.aptech.hrmapi.model.role.Role;
import com.aptech.hrmapi.repository.RoleRepository;
import com.aptech.hrmapi.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Override
    public RoleDto saveOrUpdate(RoleDto dto, AddOrUpdateType type) {
        if (type.getType()) {

        }
        return null;
    }

    private RoleDto createRole(RoleDto dto) {
        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDto(role);
    }
}
