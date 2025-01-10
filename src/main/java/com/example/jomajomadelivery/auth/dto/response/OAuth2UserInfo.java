package com.example.jomajomadelivery.auth.dto.response;

import com.example.jomajomadelivery.auth.oauth.SocialProvider;

public interface OAuth2UserInfo {

    SocialProvider getProvider();

    String getProviderId();

    String getName();

    String getEmail();
}
