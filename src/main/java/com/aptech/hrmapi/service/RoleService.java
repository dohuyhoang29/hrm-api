package com.aptech.hrmapi.service;

import com.aptech.hrmapi.common.AddOrUpdateType;
import com.aptech.hrmapi.dto.RoleDTO;
import com.aptech.hrmapi.model.role.RolePermission;
import com.aptech.hrmapi.request.RoleRequest;
import com.aptech.hrmapi.response.PageResponse;

import java.util.List;

public interface RoleService {
    PageResponse<RoleDTO> getAllRole(RoleRequest request);
    RoleDTO getRole(Long id);
    void assignRoleToUser(String username, String roleCode);
    void unAssignUserFromRole(String username, String roleCode);
    RoleDTO saveOrUpdate(RoleDTO dto, AddOrUpdateType type);
    void changeStatus(Long id, Integer status);
    void deleteRole(Long id);
}
