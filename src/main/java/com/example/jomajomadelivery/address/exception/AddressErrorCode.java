package com.example.jomajomadelivery.address.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressErrorCode implements ErrorCode {
    ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND", "ADDRESS-001", "해당 주소를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}