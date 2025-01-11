package com.example.jomajomadelivery.account.oauth2.service.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        // 실패 원인을 로그로 출력
        System.out.println("Authentication failed: " + exception.getMessage());

        // 실패 시 리다이렉트
        response.sendRedirect("/login?error=true&message=" + exception.getMessage());
    }
}
