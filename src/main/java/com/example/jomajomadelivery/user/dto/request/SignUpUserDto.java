package com.example.jomajomadelivery.user.dto.request;

import com.example.jomajomadelivery.user.entity.Role;

public record SignUpUserDto(
        String email,
        String password,
        String name,
        Role role,
        String addressName,
        String zipcode,
        String state,
        String city,
        String street,
        String detailAddress,
        String nickname,
        String phoneNumber
) {
}
