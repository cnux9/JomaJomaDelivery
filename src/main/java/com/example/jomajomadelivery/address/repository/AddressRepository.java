package com.example.jomajomadelivery.address.repository;


import com.example.jomajomadelivery.address.dto.response.AddressResponseDto;
import com.example.jomajomadelivery.address.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {
    // TODO: Order 추가될 경우
    //    r.order.orderId,

    // TODO: USER, STORE 객체로 대체
    //            r.store.storeId,
    //            r.user.userId,
    //        ) FROM Review r WHERE r.store.storeId = :storeId""")

    // TODO: 정렬 기능 추가
    // TODO: type = user 하드코딩 변경
    @Query("""
            SELECT new com.example.jomajomadelivery.address.dto.response.AddressResponseDto (
                a.addressId,
                a.createdAt
            ) FROM Address a WHERE a.type = "USER" AND a.entityId = :userId""")
    Page<AddressResponseDto> findByUserId(@Param("userId") Long userId, Pageable pageable);
}
