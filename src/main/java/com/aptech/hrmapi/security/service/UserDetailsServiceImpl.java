package com.aptech.hrmapi.security.service;

import com.aptech.hrmapi.dto.response.FeatureActionResponse;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.model.role.Role;
import com.aptech.hrmapi.repository.RolePermissionRepository;
import com.aptech.hrmapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found: " + username)
        );
        List<FeatureActionResponse> featureActionResponses = new ArrayList<>();
        for (Role role: user.getRoles()) {
            List<FeatureActionResponse> data = rolePermissionRepository.getAllRolePermission(role.getId());
            featureActionResponses.addAll(data);
        }
        return UserPrincipal.builder().user(user).featureActionResponses(featureActionResponses).build();
    }
}
