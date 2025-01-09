package com.example.jomajomadelivery.review.entity;

import com.example.jomajomadelivery.common.BaseEntity;
import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    // TODO:USER로 변경
    @Column(name = "user_id")
    private Long userId;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "store_id")
//    private Store store;
    // TODO:STORE로 변경
    @Column(name = "store_id")
    private Long storeId;


    private String contents;

    @Column(name = "img_path")
    private String imgPath;

    @Column(columnDefinition = "TINYINT")
    private Integer rating;

//    public static Review createReview(User user, Store store, ReviewCreateRequestDto dto) {
    public static Review createReview(Long userId, Long storeId, ReviewCreateRequestDto dto) {
        return Review.builder()
//                .user(user)
                .userId(userId)
//                .store(store)
                .storeId(storeId)
                .contents(dto.contents())
                .imgPath(dto.imgPath())
                .rating(dto.rating())
                .build();
    }

    public void update(ReviewUpdateRequestDto dto) {
        this.contents = dto.contents();
        this.imgPath = dto.imgPath();
        this.rating = dto.rating();
    }
}
