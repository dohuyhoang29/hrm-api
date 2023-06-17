package com.aptech.hrmapi.repository;

import com.aptech.hrmapi.model.role.RoleAssign;
import com.aptech.hrmapi.model.role.RoleAssignId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoleAssignRepository extends JpaRepository<RoleAssign, RoleAssignId> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RoleAssign ra WHERE ra.user.id = :userId AND ra.role.id = :roleId")
    void deleteUserFromRole(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
