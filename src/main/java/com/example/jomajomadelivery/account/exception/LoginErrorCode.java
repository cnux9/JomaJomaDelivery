package com.example.jomajomadelivery.account.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LoginErrorCode implements ErrorCode {
    INVALID_CREDENTIALS(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ACCOUNT-100", "아이디(이메일) 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
