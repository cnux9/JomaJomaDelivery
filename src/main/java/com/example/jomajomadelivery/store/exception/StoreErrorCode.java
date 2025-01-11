package com.example.jomajomadelivery.store.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StoreErrorCode implements ErrorCode {

    STORE_NOT_FOUND(HttpStatus.NOT_FOUND,"Store Not Found","STORE-001","해당 가게를 찾을 수 없습니다."),
    EXCEED_MAX_STORE(HttpStatus.BAD_REQUEST, "BAD REQUEST", "STORE-002", "최대 3개의 점포를 등록할 수 있습니다."),
    NOT_SELLER(HttpStatus.BAD_REQUEST, "BAD REQUEST", "STORE-003", "점장의 경우에만 점포를 생성할 수 있습니다.");
    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
