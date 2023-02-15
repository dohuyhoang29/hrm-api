package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.response.FeatureActionResponse;

import java.util.List;

public interface RolePermissionRepositoryCustom {
    List<FeatureActionResponse> getAllRolePermission(Long roleId);
}
