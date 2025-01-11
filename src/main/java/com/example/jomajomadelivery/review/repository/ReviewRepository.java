package com.example.jomajomadelivery.review.repository;

import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Order 추가될 경우
    //    r.order.orderId,
    @Query("""
        SELECT new com.example.jomajomadelivery.review.dto.response.ReviewResponseDto(
            r.reviewId,
            r.store.storeId,
            r.user.userId,
            r.contents,
            r.rating,
            r.imgPath,
            r.createdAt
        )
        FROM Review r
        WHERE r.store.storeId = :storeId AND r.rating BETWEEN :minRating AND :maxRating
        ORDER BY r.createdAt DESC
        """)
    Page<ReviewResponseDto> findByStoreId(@Param("storeId") Long storeId, @Param("minRating") Integer minRating, @Param("maxRating") Integer maxRating, Pageable pageable);
}
