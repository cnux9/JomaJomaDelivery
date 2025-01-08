package com.example.jomajomadelivery.review.service;

import com.example.jomajomadelivery.review.dto.request.ReviewRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;


    @Transactional
    public ReviewResponseDto create(ReviewRequestDto dto) {
        Review review = new Review(dto);
        Review savedReview = reviewRepository.save(review);

        return ReviewResponseDto.toDto(savedReview);
    }
}
