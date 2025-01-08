package com.example.jomajomadelivery.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Table(name = "reviews")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
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
    private int rating;

    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;

    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;
}
