package com.example.jomajomadelivery.auth.dto.response;

import com.example.jomajomadelivery.auth.oauth.SocialProvider;

import java.util.Map;

public class NaverUserInfoResponse implements OAuth2UserInfo {

    private final Map<String, Object> attribute;

    private final SocialProvider socialProvider = SocialProvider.NAVER;

    public NaverUserInfoResponse(Map<String, Object> attributes) {
        this.attribute = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public SocialProvider getProvider() {
        return socialProvider;
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getName() {
        return attribute.get("name").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }
}