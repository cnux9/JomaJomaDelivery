package com.example.jomajomadelivery.auth.oauth2.handler;

import com.example.jomajomadelivery.auth.jwt.TokenProvider;
import com.example.jomajomadelivery.auth.oauth2.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        log.info("principle={},{}", principal.getAttributes(), principal.getName());

        String email = principal.getName();

        List<String> roles = principal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String token = tokenProvider.createToken(email, roles);

        response.addCookie(createCookie(token));
        response.sendRedirect("/");
    }

    private Cookie createCookie(String token) {
        Cookie cookie = new Cookie("Authorization", token);
        cookie.setMaxAge(60 * 60 * 60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
