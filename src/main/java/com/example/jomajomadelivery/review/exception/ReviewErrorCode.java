package com.example.jomajomadelivery.review.exception;

import com.example.jomajomadelivery.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ReviewErrorCode implements ErrorCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "NOT FOUND", "REVIEW-001", "해당 리뷰를 찾을 수 없습니다."),
    RATING_NOT_VALID(HttpStatus.BAD_REQUEST, "BAD REQUEST", "REVIEW-002", "평점은 1-5 사이의 정수만 가능합니다."),
    CONTENTS_LENGTH_NOT_VALID(HttpStatus.BAD_REQUEST, "BAD REQUEST", "REVIEW-003", "리뷰는 200자까지만 가능합니다.");

    private final HttpStatus status;
    private final String name;
    private final String code;
    private final String message;
}
