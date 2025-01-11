package com.example.jomajomadelivery.auth.dto.request;

import com.example.jomajomadelivery.auth.oauth2.SocialProvider;
import com.example.jomajomadelivery.user.entity.Role;

public record SignUpUserDto(
        SocialProvider socialType,
        String providerId,
        String email,
        String password,
        String name,
        String nickName,
        String phoneNumber,
        String addressName,
        String zipcode,
        String state,
        String city,
        String street,
        String detailAddress,
        Role role
) {
    public static SignUpUserDto empty() {
        return new SignUpUserDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public static SignUpUserDto createFromSocialLoginDto(
            String name,
            String email,
            SocialProvider socialType,
            String providerId
    ) {
        return new SignUpUserDto(
                socialType,
                providerId,
                email,
                null,
                name,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
