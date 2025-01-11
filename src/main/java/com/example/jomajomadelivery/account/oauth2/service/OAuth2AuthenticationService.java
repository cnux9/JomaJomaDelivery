package com.example.jomajomadelivery.account.oauth2.service;

import com.example.jomajomadelivery.account.oauth2.dto.response.OAuth2UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class OAuth2AuthenticationService extends DefaultOAuth2UserService {
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

        return new CustomOAuth2User(socialType, providerId, email, name);
    }
}
