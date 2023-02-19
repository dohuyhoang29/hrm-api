package com.aptech.hrmapi.repository.impl;

import com.aptech.hrmapi.response.FeatureActionResponse;
import com.aptech.hrmapi.repository.RolePermissionRepositoryCustom;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class RolePermissionRepositoryCustomImpl implements RolePermissionRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<FeatureActionResponse> getAllRolePermission(Long roleId) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append("select action_code as 'actionCode', b.feature_code as 'featureCode' " +
                "from action a, feature b, role_feature_allowed r " +
                "where a.id = r.action_id and b.id = r.feature_id");
        if (roleId != null) strQuery.append(" and r.role_id = :roleId");
        Query query = entityManager.createQuery(strQuery.toString()).setParameter("roleId", roleId);

        return query.getResultList();
    }
}
