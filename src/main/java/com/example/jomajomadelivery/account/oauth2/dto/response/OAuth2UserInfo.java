package com.example.jomajomadelivery.account.auth.dto.response;

import com.example.jomajomadelivery.account.auth.oauth2.SocialProvider;

public interface OAuth2UserInfo {

    SocialProvider getProvider();

    String getProviderId();

    String getName();

    String getEmail();
}
