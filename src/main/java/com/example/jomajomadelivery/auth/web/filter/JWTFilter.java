package com.example.jomajomadelivery.auth.web.filter;

import com.example.jomajomadelivery.auth.jwt.TokenProvider;
import com.example.jomajomadelivery.auth.oauth2.CustomOAuth2User;
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
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private static final String[] WHITE_LIST = {"/", "/signup", "/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        /**
         * filter 가 불필요한 url 검증
         */
        if (isStaticResource(requestURI)) {
            log.info("Skip filter for url: {}", requestURI);

            filterChain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = request.getCookies();
        String token = getCookieValue(cookies);

        /**
         * 토큰 검증
         */
        if (token == null) {
            log.info("token null");

            filterChain.doFilter(request, response);
            return;
        }

        /**
         * 토큰 만료 시간 검증
         */
        if (Boolean.TRUE.equals(tokenProvider.isExpired(token))) {
            log.info("token expired");

            filterChain.doFilter(request, response);
            return;
        }

        String email = tokenProvider.getEmail(token);
        List<String> roles = tokenProvider.getRole(token);

        CustomOAuth2User customOAuth2User = new CustomOAuth2User(email, roles);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        for (String pattern : WHITE_LIST) {
            if (PatternMatchUtils.simpleMatch(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    private boolean isStaticResource(String url) {
        return url.startsWith("/css/")
                || url.startsWith("/js/")
                || url.startsWith("/favicon.ico/")
                || url.startsWith("/images/");
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
