package com.example.jomajomadelivery.review.dto.request;

public record ReviewCreateRequestDto(
        Long orderId,
        String contents,
        Integer rating,
        String imgPath
) {
}
