package com.example.jomajomadelivery.review.repository;

import com.example.jomajomadelivery.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Order 추가될 경우
    //    r.order.orderId,
//    @Query("""
//        SELECT new com.example.jomajomadelivery.review.dto.respons.ReviewResponseDto(
//            r.reviewId,
//            r.store.storeId,
//            r.user.userId,
//            r.contents,
//            r.rating,
//            r.imgPath,
//            r.createdAt
//        ) FROM Review r WHERE r.store.storeId = :storeId""")
//    Page<ReviewResponseDto> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
