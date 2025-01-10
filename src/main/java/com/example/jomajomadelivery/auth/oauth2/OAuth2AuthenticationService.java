package com.example.jomajomadelivery.auth.oauth;

import com.example.jomajomadelivery.auth.dto.response.OAuth2UserInfo;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2AuthenticationService extends DefaultOAuth2UserService {

    private final UserService userService;

    /**
     * 리소스 서버에서 제공되는 데이터를 받음
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Social attributes: {}", attributes);

        // 서비스에 따라 유저정보를 다른 방식으로 추출
        OAuth2UserInfo oAuth2UserInfo = SocialProvider
                .fromString(registrationId)
                .getOAuth2UserInfo(attributes);

        String providerId = oAuth2UserInfo.getProviderId();
        SocialProvider socialType = oAuth2UserInfo.getProvider();
        String name = oAuth2UserInfo.getName();
        String email = oAuth2UserInfo.getEmail();

        log.info("social={}", socialType);

        Optional<User> userOptional = userService.findBySocialTypeAndProviderId(socialType, providerId);

        if (userOptional.isEmpty()) {
            saveToSession(providerId, name, email);
            throw new OAuth2AuthenticationException(new OAuth2Error("redirect_signup"), "Redirect to signup page required");
        }

        return new CustomOAuth2User(userOptional.get().getEmail(), List.of(userOptional.get().getRole().toString()));
    }

    /**
     * 소셜 로그인이 처음인 회원은 해당 세션을 가지고 추가 정보 입력 뷰로 리다이렉트
     */
    private void saveToSession(String providerId, String name, String email) {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest()
                .getSession();

        session.setAttribute("signup_provider_id", providerId);
        session.setAttribute("signup_name", name);
        session.setAttribute("signup_email", email);

        log.info("추가 정보 입력 폼 리다이렉트 (추가정보): providerId={}, name={}, email={}", providerId, name, email);
    }
}
