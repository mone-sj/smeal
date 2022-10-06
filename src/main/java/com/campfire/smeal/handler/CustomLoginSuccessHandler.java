package com.campfire.smeal.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // Authentication 객체를 이용해서 사용자가 가진 모든 권한을 체크
        log.warn("Login Success");
        List<String> roleNames = new ArrayList<>();
        authentication.getAuthorities().forEach(authority->{
            roleNames.add(authority.getAuthority());
        });
        log.warn("ROLE NAMES : "+roleNames);
        if(roleNames.contains("ROLE_ADMIN")) {
            response.sendRedirect("/admin/statistics");
            return;
        }
        if(roleNames.contains("ROLE_USER")) {
            response.sendRedirect("/dashboard");
            return;
        }
        //response.sendRedirect("/");
    }
}
