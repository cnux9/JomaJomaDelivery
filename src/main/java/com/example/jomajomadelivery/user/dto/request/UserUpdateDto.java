package com.example.jomajomadelivery.user.dto.request;

public record UserUpdateDto(
        String email,
        String password,
        String name,
        String nickname,
        String phoneNumber
) {
}
