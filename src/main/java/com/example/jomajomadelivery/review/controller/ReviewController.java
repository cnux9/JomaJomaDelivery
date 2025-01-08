package com.example.jomajomadelivery.review.controller;

import com.example.jomajomadelivery.review.dto.request.ReviewRequestDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stores/{storeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


    @PostMapping
    public void create(ReviewRequestDto dto) {
        reviewService.create(dto);
    }

}
