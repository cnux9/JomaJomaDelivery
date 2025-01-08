package com.example.jomajomadelivery.review.dto.response;

import com.example.jomajomadelivery.review.entity.Review;

public record ReviewResponseDto() {
    public static ReviewResponseDto toDto(Review savedReview) {
        return new ReviewResponseDto(
                savedReview.getReviewId(),
                savedReview.getReviewId(),
        );
    }
}
