package com.example.jomajomadelivery.account.oauth2.service.handler;

import com.example.jomajomadelivery.account.auth.service.UserAuthService;
import com.example.jomajomadelivery.account.jwt.TokenProvider;
import com.example.jomajomadelivery.account.oauth2.service.CustomOAuth2User;
import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;
import com.example.jomajomadelivery.user.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private final UserAuthService userAuthService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User principal = (CustomOAuth2User) authentication.getPrincipal();
        String providerId = principal.getProviderId();
        SocialProvider socialType = principal.getProvider();
        String name = principal.getName();
        String email = principal.getEmail();

        Optional<User> optionalUser = userAuthService.findBySocialTypeAndProviderId(socialType, providerId);

        /**
         * 소셜로그인이 처음인 유저는 추가 정보 입력 API 로 리다이렉트
         */
        if (optionalUser.isEmpty()) {
            String redirectUrl = String.format(
                    "/signup/additional-info?name=%s&email=%s&socialType=%s&providerId=%s",
                    encode(name, UTF_8),
                    encode(email, UTF_8),
                    encode(socialType.name(), UTF_8),
                    encode(providerId, UTF_8)

            );
            response.sendRedirect(redirectUrl);
            return;
        }

        User user = optionalUser.get();
        String token = tokenProvider.createToken(user.getUserId(), user.getRole());

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
