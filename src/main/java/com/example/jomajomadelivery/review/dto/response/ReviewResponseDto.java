package com.example.jomajomadelivery.review.dto.response;

import com.example.jomajomadelivery.review.entity.Review;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long id,
//        Long storeId,
//        Long orderId,
//        Long userId,
        String contents,
        int rating,
        String imgPath,
        LocalDateTime createdAt
) {
    public static ReviewResponseDto toDto(Review savedReview) {
        return new ReviewResponseDto(
                savedReview.getId(),
//                savedReview.getStore().getId(),
//                savedReview.getOrder().getId(),
//                savedReview.getUser().getId,
                savedReview.getContents(),
                savedReview.getRating(),
                savedReview.getImgPath(),
                savedReview.getCreatedAt()
        );
    }
}
