package com.example.jomajomadelivery.review.entity;

import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @ManyToOne
//    @JoinColumn(name = "store_id")
//    private Store store;


    private String contents;

    @Column(name = "img_path")
    private String imgPath;

    @Column(columnDefinition = "TINYINT")
    private Integer rating;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    public Review(ReviewCreateRequestDto dto) {
        this.contents = dto.contents();
        this.imgPath = dto.imgPath();
        this.rating = dto.rating();
    }

    public static Review createReview(ReviewCreateRequestDto dto) {
        return Review.builder()
                .contents(dto.contents())
                .imgPath(dto.imgPath())
                .rating(dto.rating())
                .build();
    }
}
