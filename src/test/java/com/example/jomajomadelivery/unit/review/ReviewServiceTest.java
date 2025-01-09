package com.example.jomajomadelivery.review;

import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import com.example.jomajomadelivery.review.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewServiceTest {
//    @Autowired
    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository

    @Test
    void 리뷰_생성() {
        // Given


        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto(orderId, contents, rating, imgPath);

        Review createdReview = new Review(storeId, requestDto);
        when(reviewRepository.save(any(Review.class))).thenReturn(createdReview);

        // When
        ReviewResponseDto responseDto = reviewService.create(storeId, requestDto);

        // Then
        assertThat
//        assertsThat
    }
}
