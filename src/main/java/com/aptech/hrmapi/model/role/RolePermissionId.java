package com.aptech.hrmapi.model.role;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class RolePermissionId implements Serializable {
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "feature_id")
    private Long featureId;

    @Column(name = "action_id")
    private Long actionId;
}
