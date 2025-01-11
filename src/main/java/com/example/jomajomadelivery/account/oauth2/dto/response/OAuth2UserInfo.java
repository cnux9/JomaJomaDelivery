package com.example.jomajomadelivery.account.oauth2.dto.response;

import com.example.jomajomadelivery.account.oauth2.service.SocialProvider;

public interface OAuth2UserInfo {

    SocialProvider getProvider();

    String getProviderId();

    String getName();

    String getEmail();
}
