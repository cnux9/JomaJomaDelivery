package com.example.jomajomadelivery.store.entity;

import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.menu.entity.Menu;
import com.example.jomajomadelivery.store.dto.request.StoreRequestDto;
import com.example.jomajomadelivery.store.dto.request.UpdateStoreRequestDto;
import com.example.jomajomadelivery.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stores")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동 생성
    @Column(name = "store_id")
    private Long storeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, unique = true)
    private Long addressId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "img_path")
    private String imgPath;

    @Column(name = "open_time")
    private LocalTime openTime;

    @Column(name = "close_time")
    private LocalTime closeTime;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "min_order_price")
    private Integer minOrderPrice;

    @Column(name = "delivery_price")
    private Integer deliveryPrice;

    @Column(name = "favorite_count", nullable = false)
    private Long favoriteCount;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Menu> menus = new ArrayList<>();

    public static Store addStore(User user, StoreRequestDto dto,
                                 String imgPath,Long addressId){
        return Store.builder()
                .user(user)
                .category(Category.valueOf(dto.category()))
                .name(dto.name())
                .description(dto.description())
                .addressId(addressId)
                .phoneNumber(dto.phoneNumber())
                .imgPath(imgPath)
                .openTime(dto.openTime())
                .closeTime(dto.closeTime())
                .minOrderPrice(dto.minOrderPrice())
                .deliveryPrice(dto.deliveryPrice())
                .favoriteCount(0L)
                .rating(0.0)
                .isDeleted(false)
                .build();
    }

    public Store updateStore(UpdateStoreRequestDto dto,String imgPath){
        this.description=dto.description();
        this.imgPath=imgPath;
        this.openTime=dto.openTime();
        this.closeTime=dto.closeTime();
        this.minOrderPrice=dto.minOrderPrice();
        this.deliveryPrice=dto.deliveryPrice();
        return this;
    }

    public void shutDownStore(){
        this.isDeleted=true;
    }

    public void updateRating(Double rating) {
        this.rating = rating;
    }

    public void updateFavoriteCount(Long favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}