package com.example.jomajomadelivery.address.repository;


import com.example.jomajomadelivery.address.entity.Address;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // Order 추가될 경우
    //    r.order.orderId,

    // USER, STORE 객체로 대체
    //            r.store.storeId,
    //            r.user.userId,
    //        ) FROM Review r WHERE r.store.storeId = :storeId""")
    @Query("""
            SELECT new com.example.jomajomadelivery.address.dto.response.AddressResponseDto (
                r.reviewId,
                r.storeId,
                r.userId,
                r.contents,
                r.rating,
                r.imgPath,
                r.createdAt
            ) FROM Review r WHERE r.storeId = :storeId""")
    Page<ReviewResponseDto> findByStoreId(@Param("storeId") Long storeId, Pageable pageable);
}
