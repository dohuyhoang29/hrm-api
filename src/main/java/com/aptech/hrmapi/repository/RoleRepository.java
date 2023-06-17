package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleCode(String roleCode);
}
