package com.aptech.hrmapi.service;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.RoleDto;

public interface RoleService {
    RoleDto saveOrUpdate(RoleDto dto, AddOrUpdateType type);
}
