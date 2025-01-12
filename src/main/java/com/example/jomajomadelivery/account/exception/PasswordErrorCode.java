package com.example.jomajomadelivery.account.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PasswordErrorCode implements ErrorCode {
    PASSWORD_EMPTY(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ACCOUNT-010", "유효한 비밀번호를 입력해주세요."),
    PASSWORD_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ACCOUNT-020", "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함하여 8자 이상이어야 합니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
