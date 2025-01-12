package com.example.jomajomadelivery.account.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum EmailErrorCode implements ErrorCode {
    EMAIL_ALREADY_USED(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ACCOUNT-001", "이미 사용중인 이메일 입니다."),
    EMAIL_EMPTY(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ACCOUNT-002", "유효한 이메일을 입력해주세요."),
    EMAIL_INVALID_FORMAT(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ACCOUNT-003", "이메일 형식이 맞지 않습니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
