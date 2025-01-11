package com.example.jomajomadelivery.cart.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CartErrorCode implements ErrorCode {
    CART_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND", "CART-001", "해당 장바구니을 찾을 수 없습니다.");

//    EXCEED_MAX_STORE(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ITEM-004", "최대 3개의 점포를 등록할 수 있습니다."),
//    NOT_SELLER(HttpStatus.BAD_REQUEST, "BAD REQUEST", "ITEM-004", "점장의 경우에만 점포를 생성할 수 있습니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
