package com.example.jomajomadelivery.auth.dto.request;

import com.example.jomajomadelivery.user.entity.Role;

public record SignUpUserDto(
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
                "","","","","","","","","","","",Role.ROLE_USER
        );
    }
}
