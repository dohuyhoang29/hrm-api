package com.aptech.hrmapi.response;

import lombok.Data;

import java.util.Collection;
import java.util.Set;

@Data
public class UserInfoResponse {
    private Long userId;
    private String username;
    private Collection authorities;
    private Set<FeatureActionResponse> featureActions;

    public UserInfoResponse() {

    }

    public UserInfoResponse(Long userId, String username, Collection authorities, Set<FeatureActionResponse> featureActions) {
        this.userId = userId;
        this.username = username;
        this.authorities = authorities;
        this.featureActions = featureActions;
    }
}
