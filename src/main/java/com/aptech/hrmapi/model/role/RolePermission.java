package com.aptech.hrmapi.model.role;

import com.aptech.hrmapi.model.Action;
import com.aptech.hrmapi.model.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "role_feature_allowed")
public class RolePermission {
    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @MapsId("featureId")
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @ManyToOne
    @MapsId("actionId")
    @JoinColumn(name = "action_id")
    private Action action;

    public RolePermission(Role role, Feature feature, Action action) {
        this.id = new RolePermissionId(role.getId(), feature.getId(), action.getId());
        this.role = role;
        this.feature = feature;
        this.action = action;
    }
}
