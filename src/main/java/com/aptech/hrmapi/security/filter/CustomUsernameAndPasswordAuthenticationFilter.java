package com.aptech.hrmapi.security.filter;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.model.User;
import com.aptech.hrmapi.repository.RolePermissionRepository;
import com.aptech.hrmapi.repository.UserRepository;
import com.aptech.hrmapi.response.FeatureActionResponse;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.response.UserInfoResponse;
import com.aptech.hrmapi.security.service.UserPrincipal;
import com.aptech.hrmapi.security.utils.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CustomUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public CustomUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/eproject4/auth/**", "POST"));
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserCredentials creds = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);

            ApplicationContext context = SpringContext.getApplicationContext();
            UserRepository userRepository = (UserRepository) context.getBean("UserRepository");
            Optional<User> user = userRepository.findByUsername(creds.getUsername());

            //validate username
            if (!user.isPresent()) {
                ResponseBody responseBody = new ResponseBody(Response.USERNAME_INVALID);
                sendResponse(responseBody, response);
                return null;
            }// validate password
            BCryptPasswordEncoder passwordEncoder = (BCryptPasswordEncoder) context.getBean("PasswordEncoder");
            String passwordInDB = user.get().getPassword();
            boolean matches = passwordEncoder.matches(creds.getPassword(), passwordInDB);
            if(!matches) {
                ResponseBody responseBody = new ResponseBody(Response.PASSWORD_INVALID);
                sendResponse(responseBody, response);
                return null;
            }

            // validate account status
            if(user.get().getStatus() == 0) {
                ResponseBody responseBody = new ResponseBody(Response.USER_INACTIVE);
                sendResponse(responseBody, response);
                return null;
            }

            // assign spring security authenticate user .
            // this will be manage by AuthenticationManager
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    creds.getUsername(), creds.getPassword(), Collections.emptyList());

            return authenticationManager.authenticate(auth);

        } catch (Exception e) {
            sendResponse(responseError(), response);
            return null;
        }
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication auth) {
        try {
            // if user was verified username, password, status
            // check user white list, user black list
            UserPrincipal userPrinciple = (UserPrincipal) auth.getPrincipal();
            Long userId = userPrinciple.getId();
            ApplicationContext context = SpringContext.getApplicationContext();

            // then if pass send userInfo to client
            RolePermissionRepository rolePermissionRepository = (RolePermissionRepository) context.getBean("RolePermissionRepository");
            List<FeatureActionResponse> datas = new ArrayList<>();
            for (Long roleId : userPrinciple.getRoleIds()) {
                List<FeatureActionResponse> data = rolePermissionRepository.getAllRolePermission(roleId);
                datas.addAll(data);
            }
            Set<FeatureActionResponse> result = new HashSet<>(datas);
            UserInfoResponse userInfo = new UserInfoResponse(userId, userPrinciple.getUsername(), userPrinciple.getAuthorities(), result);

            ResponseBody responseBody = new ResponseBody(Response.SUCCESS, userInfo);
            sendResponse(responseBody, response);
        } catch (Exception e) {
            sendResponse(responseError(), response);
        }
    }

    private void sendResponse(ResponseBody responseBody, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Gson gson = new Gson();
        String objString = gson.toJson(responseBody);
        PrintWriter out = response.getWriter();
        out.println(objString);
        out.flush();
        out.close();
    }

    private ResponseBody responseError() {
        return new ResponseBody(Response.SYSTEM_ERROR);
    }

    public static class UserCredentials {
        private String username;

        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
