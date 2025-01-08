package com.example.jomajomadelivery.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class NaverOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest
                .getClientRegistration()
                .getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        log.info("Naver attributes: {}", attributes);

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) {
            throw new OAuth2AuthenticationException(new OAuth2Error("invalid response"), "No response from Naver");
        }

        String id = (String) response.get("id");
        String nickname = (String) response.get("nickname");
        String email = (String) response.get("email");

        //    DB 연동: 해당 naverId나 email로
        //    이미 가입한 사용자가 있는지 조회 → 없으면 가입, 있으면 업데이트 or 로그인
        //    여기서는 예시로 "User" 엔티티에 "socialId" 등을 매핑한다 가정
        //    실제로는 UserRepository, UserService 등을 주입받아 처리

        //    시큐리티 세션(Principal)에 저장할 객체 생성
        //    DefaultOAuth2User를 그대로 반환하거나,
        //    CustomUserPrincipal 등을 만들어 반환할 수도 있음

        Collection<? extends GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));

        return new DefaultOAuth2User(authorities, attributes, "email");
    }
}
