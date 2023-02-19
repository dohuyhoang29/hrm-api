package com.aptech.hrmapi.response;

import lombok.Data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Collection authorities;
    private List<Long> roleIds;
    private Set<FeatureActionResponse> featureActions;

    public JwtResponse(String accessToken, String username, Collection authorities) {
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
    }

    public Set<FeatureActionResponse> getFeatureActions() {
        return featureActions;
    }

    public void setFeatureActions(Set<FeatureActionResponse> featureActions) {
        this.featureActions = featureActions;
    }

    public JwtResponse(String accessToken, String username, Collection authorities, List<Long> roleIds,
                       Set<FeatureActionResponse> featureActions) {
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
        this.roleIds = roleIds;
        this.featureActions = featureActions;
    }
}
