package com.example.jomajomadelivery.store.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements ErrorCode {
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND", "STORE-001", "해당 가게를 찾을 수 없습니다."),

    EXCEED_MAX_STORE(HttpStatus.BAD_REQUEST, "BAD REQUEST", "STORE-002", "가게는 3개까지 등록할 수 있습니다."),
    NOT_SELLER(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "STORE-003", "사장님만 점포를 생성할 수 있습니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
