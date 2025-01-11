package com.example.jomajomadelivery.account.auth.dto.request;

public record LoginUserDto(String email, String password) {
    public static LoginUserDto empty() {
        return new LoginUserDto(null, null);
    }
}
