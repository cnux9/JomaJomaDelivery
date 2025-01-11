package com.example.jomajomadelivery.account.auth.oauth2;

import com.example.jomajomadelivery.account.auth.dto.response.NaverUserInfoResponse;
import com.example.jomajomadelivery.account.auth.dto.response.OAuth2UserInfo;

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
            return null;
        }
    },
    GOOGLE {
        @Override
        public OAuth2UserInfo getOAuth2UserInfo(Map<String, Object> attributes) {
            return null;
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
