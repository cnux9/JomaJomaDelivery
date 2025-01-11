package com.example.jomajomadelivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseEntity> handleCustomException(CustomException ex) {
        return ErrorResponseEntity
                .toResponseEntity(ex.getErrorCode());
    }

    @ExceptionHandler(FileSaveException.class)
    public ResponseEntity<ErrorResponseEntity> handleIOException() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponseEntity.builder()
                                .status(400)
                                .name("잘못된 요청")
                                .code("IMAGE-0010")
                                .message("이미지 형식 또는 이미지의 크기를 확인해주세요.")
                                .build()
                );
    }
}
