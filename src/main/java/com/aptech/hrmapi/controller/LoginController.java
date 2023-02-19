package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.exception.CommonException;
import com.aptech.hrmapi.request.LoginRequest;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.security.jwt.JwtProvider;
import com.aptech.hrmapi.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/auth")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<ResponseBody> authenticateUser(@Valid @RequestBody LoginRequest request) {
        try {
            return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, loginService.login(request.getUsername(), request.getPassword())));
        } catch (CommonException e) {
            return ResponseEntity.ok(new ResponseBody(e.getResponse().getResponseCode(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseBody(Response.SYSTEM_ERROR, e.getMessage()));
        }
    }
}
