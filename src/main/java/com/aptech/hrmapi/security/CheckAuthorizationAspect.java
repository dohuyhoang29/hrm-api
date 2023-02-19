package com.aptech.hrmapi.security;

import com.aptech.hrmapi.response.FeatureActionResponse;
import com.aptech.hrmapi.security.service.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Configuration
@Log4j2
public class CheckAuthorizationAspect {
    @Before("@annotation(authorizationInfo)")
    public void before(JoinPoint joinPoint, AuthorizationInfo authorizationInfo) {
        log.info(" before called " + joinPoint.toString());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
        boolean isValid = false;
        if (userPrinciple.getFeatureActions() != null) {
            for (FeatureActionResponse featureAction : userPrinciple.getFeatureActions()) {
                if (featureAction.getActionCode().equals(authorizationInfo.actionCode()) && featureAction.getFeatureCode().equals(authorizationInfo.featureCode())) {
                    isValid = true;
                    break;
                }
            }
        }
        if(!isValid)
            throw new AccessDeniedException("User khong co quyen truy cap chuc nang nay");
    }
}
