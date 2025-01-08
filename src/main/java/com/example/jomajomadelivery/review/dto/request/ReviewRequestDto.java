package com.example.jomajomadelivery.review.dto.request;

public record ReviewRequestDto(
        Long orderId,
        String contents,
        Integer rating,
        String imgPath
) {
}
