package com.example.jomajomadelivery.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseEntity {
    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(
                        ErrorResponseEntity.builder()
                                .status(errorCode.getStatus().value())
                                .name(errorCode.getName())
                                .code(errorCode.getCode())
                                .message(errorCode.getMessage())
                                .build()
                );
    }
}
