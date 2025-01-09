package com.example.jomajomadelivery.user.dto.request;

public record SignUpUserDto(
        String email,
        String password,
        String name,
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
