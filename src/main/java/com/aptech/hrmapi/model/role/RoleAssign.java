package com.aptech.hrmapi.model.role;

import com.aptech.hrmapi.model.User;
import javax.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "assigned_role")
public class RoleAssign {
    @EmbeddedId
    private RoleAssignId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @Column(name = "modified_date", nullable = false)
    private Date modifiedDate;

    public RoleAssign(User user, Role role, Date now, String username) {
        this.id = new RoleAssignId(user.getId(), role.getId());
        this.user = user;
        this.role = role;
        this.createdBy = username;
        this.createdDate = now;
        this.modifiedBy = username;
        this.modifiedDate = now;
    }
}
