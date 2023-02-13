package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.model.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
