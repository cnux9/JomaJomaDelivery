package com.example.jomajomadelivery.store.entity;

import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    @Column(name = "store_id")
    private Long storeId;

    //Todo: userid대신 User객체 사용해야함.
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "category")
    private String category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "img_path")
    private String img_path;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    /**
     * precision: 전체 자리수 123.45 이면 5
     * scale: 소수점 자리수 2 이면 0.nn
     */
    @Column(name = "rating")
    private Double rating;

    @Column(name = "min_order_price")
    private int minOrderPrice;

    @Column(name = "delivery_price")
    private int deliveryPrice;

    @Column(name = "favorite_count")
    private Long favoriteCount;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    public Store addStore(Long user_id,StoreRequestDto dto){
        this.userId=user_id;
        this.category=dto.getCategory();
        this.name=dto.getName();
        this.description=dto.getDescription();
        this.address=dto.getAddress();
        this.img_path=dto.getImgPath();
        this.openTime=dto.getOpenTime();
        this.closeTime=dto.getCloseTime();
        this.minOrderPrice=dto.getMinOrderPrice();
        this.deliveryPrice=dto.getDeliveryPrice();
        return this;
    }

}