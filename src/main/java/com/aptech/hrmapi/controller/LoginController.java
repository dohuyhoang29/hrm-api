package com.aptech.hrmapi.controller;

import com.aptech.hrmapi.common.Response;
import com.aptech.hrmapi.request.LoginRequest;
import com.aptech.hrmapi.response.ResponseBody;
import com.aptech.hrmapi.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${app.domain}/auth")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/signin")
    public ResponseEntity<ResponseBody> authenticateUser(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(new ResponseBody(Response.SUCCESS, loginService.login(request.getUsername(), request.getPassword())));
    }
}
