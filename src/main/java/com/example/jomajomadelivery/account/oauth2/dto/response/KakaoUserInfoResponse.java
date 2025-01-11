package com.example.jomajomadelivery.account.oauth2.dto.response;

import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;

import java.util.Map;

public class KakaoUserInfoResponse implements OAuth2UserInfo{

    private final Map<String, Object> attributes;

    private final SocialProvider socialProvider = SocialProvider.GOOGLE;

    public KakaoUserInfoResponse(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public SocialProvider getProvider() {
        return null;
    }

    @Override
    public String getProviderId() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public String getEmail() {
        return "";
    }
}
