package com.example.jomajomadelivery.store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "stores")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    @Column(name = "store_id")
    private Long storeId;

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
    private String imgPath;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    /**
     * precision: 전체 자리수 123.45 이면 5
     * scale: 소수점 자리수 2 이면 0.nn
     */
    @Column(name = "rating", precision = 2, scale = 1)
    private Double rating;

    @Column(name = "min_order_price")
    private int minOrderPrice;

    @Column(name = "delivery_price")
    private int deliveryPrice;

    @Column(name = "favorite_count")
    private Long favoriteCount;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;
}