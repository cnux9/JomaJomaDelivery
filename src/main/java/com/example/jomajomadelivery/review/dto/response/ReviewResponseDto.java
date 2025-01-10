package com.example.jomajomadelivery.review.dto.response;

import com.example.jomajomadelivery.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long reviewId,
        Long storeId,
//        Long orderId,
        Long userId,
        String contents,
        Integer rating,
        String imgPath,
        LocalDateTime createdAt
) {
    public static ReviewResponseDto toDto(Review savedReview) {
        return new ReviewResponseDto(
                savedReview.getReviewId(),
//                savedReview.getStore().getStoreId(),
                savedReview.getStore().getStoreId(),
//                savedReview.getOrder().getId(),
//                savedReview.getUser().getUserId(),
                savedReview.getUserId(),
                savedReview.getContents(),
                savedReview.getRating(),
                savedReview.getImgPath(),
                savedReview.getCreatedAt()
        );
    }
}
