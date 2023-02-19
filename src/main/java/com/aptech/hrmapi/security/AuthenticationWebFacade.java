package com.aptech.hrmapi.security;

import org.springframework.security.core.Authentication;

public interface AuthenticationWebFacade {
    Authentication getAuthentication();
}
