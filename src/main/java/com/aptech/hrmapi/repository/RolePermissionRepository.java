package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.model.role.RolePermission;
import com.aptech.hrmapi.model.role.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId>, RolePermissionRepositoryCustom {
}
