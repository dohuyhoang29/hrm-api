package com.aptech.hrmapi.security.service;

import com.aptech.hrmapi.common.Constant;
import com.aptech.hrmapi.response.FeatureActionResponse;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPrincipal implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private User user;
    private List<FeatureActionResponse> featureActionResponses;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = user.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return authorities;
    }

    public List<Long> getRoleIds() {
        Set<Role> roles = user.getRoles();
        List<Long> roleIds = new ArrayList<>();
        for (Role role: roles) {
            roleIds.add(role.getId());
        }
        return roleIds;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return user.getStatus().equals(Constant.STATUS.ACTIVE);
    }

    public Long getId() {
        return user.getId();
    }

    public List<FeatureActionResponse> getFeatureActions() {
        return featureActionResponses;
    }

    public void setFeatureActions(List<FeatureActionResponse> featureActions) {
        this.featureActionResponses = featureActions;
    }

    public User getUser() {
        return user;
    }
}
