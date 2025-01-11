package com.example.jomajomadelivery.account.oauth2.service;

import com.example.jomajomadelivery.account.oauth2.dto.response.GoogleUserInfoResponse;
import com.example.jomajomadelivery.account.oauth2.dto.response.KakaoUserInfoResponse;
import com.example.jomajomadelivery.account.oauth2.dto.response.NaverUserInfoResponse;
import com.example.jomajomadelivery.account.oauth2.dto.response.OAuth2UserInfo;

import java.util.Map;

public enum SocialProvider {
    NAVER {
        @Override
        public OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
            return new NaverUserInfoResponse(attributes);
        }
    },
    KAKAO {
        @Override
        public OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
            return new KakaoUserInfoResponse(attributes);
        }
    },
    GOOGLE {
        @Override
        public OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
            return new GoogleUserInfoResponse(attributes);
        }
    };

    /**
     * 각 프로바이더별로 구현 할 추상 메서드
     */
    public abstract OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes);

    public static SocialProvider fromString(String providerName) {
        return SocialProvider.valueOf(providerName.toUpperCase());
    }
}
