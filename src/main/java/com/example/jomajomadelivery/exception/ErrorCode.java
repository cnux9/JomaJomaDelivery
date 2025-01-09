package com.example.jomajomadelivery.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatus();

    String getName();

    String getCode();

    String getMessage();
}
