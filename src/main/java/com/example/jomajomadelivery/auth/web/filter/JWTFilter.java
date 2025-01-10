package com.example.jomajomadelivery.auth.web.filter;

import com.example.jomajomadelivery.auth.jwt.TokenProvider;
import com.example.jomajomadelivery.auth.oauth.CustomOAuth2User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        String token = getCookieValue(cookies);

        /**
         * 토큰 검증
         */
        if (token == null) {
            log.info("token null");

            filterChain.doFilter(request, response);
        }

        /**
         * 토큰 만료 시간 검증
         */
        if (tokenProvider.isExpired(token)) {
            log.info("token expired");

            filterChain.doFilter(request, response);
        }

        String email = tokenProvider.getEmail(token);
        List<String> roles = tokenProvider.getRole(token);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(email, roles);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    private String getCookieValue(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            log.info("cookie={}", cookie.getName());

            if (cookie.getName().equals("Authorization")) {
                return cookie.getValue();
            }
        }

        return null;
    }
}
