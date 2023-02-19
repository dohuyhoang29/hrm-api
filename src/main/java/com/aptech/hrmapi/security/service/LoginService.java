package com.aptech.hrmapi.security.service;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.repository.UserRepository;
import com.aptech.hrmapi.response.FeatureActionResponse;
import com.aptech.hrmapi.response.JwtResponse;
import com.aptech.hrmapi.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public JwtResponse login(String username, String password) {
        if (!checkLogin(username)) {
            throw new CommonException(Response.LOGIN_FAIL);
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);
        Set<FeatureActionResponse> results = new HashSet<>(userPrincipal.getFeatureActions());
        return new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities(), userPrincipal.getRoleIds(), results);
    }

    public boolean checkLogin(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () ->new UsernameNotFoundException("User not found: " + username)
        );
        if (user.getStatus() == 0) {
            throw new CommonException(Response.USER_INACTIVE);
        }
        return true;
    }
}
