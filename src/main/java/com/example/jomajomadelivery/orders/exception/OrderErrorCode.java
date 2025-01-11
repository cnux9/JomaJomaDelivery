package com.example.jomajomadelivery.orders.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum OrderErrorCode implements ErrorCode {
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, HttpStatus.BAD_REQUEST.getReasonPhrase(), "ORDER-001", "해당 주문를 찾을 수 없습니다."),
    USER_CHANGES_ORDER_STATUS(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "ORDER-002", "점장만 주문 상태변경이 가능합니다."),
    LOWER_THAN_MIN_ORDER_PRICE(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "ORDER-003", "주문의 총액이 최소 주문 금액보다 적습니다."),
    STORE_NOT_OPEN(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), "ORDER-004", "해당 점포의 영업시간이 아닙니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
