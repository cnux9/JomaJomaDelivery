package com.example.jomajomadelivery.review.dto.request;

public record ReviewUpdateRequestDto(
        String contents,
        Integer rating,
        String imgPath
) {
}
