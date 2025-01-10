package com.example.jomajomadelivery.review.service;

import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.util.DumpDataFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    ReviewService reviewService;

    @Test
    void 리뷰_생성() {

        // Given
        Store mockReturnStore = DumpDataFactory.store();
        Review mockReturnReview = DumpDataFactory.review(mockReturnStore);

        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto(1L, "머리카락이 나왔어요.", 5, "someImg.jpg");
        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "머리카락이 나왔어요.", 5, "someImg.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        when(reviewRepository.save(any(Review.class))).thenReturn(mockReturnReview);
        when(storeRepository.getReferenceById(any(Long.class))).thenReturn(mockReturnStore);

        // When
        ReviewResponseDto result = reviewService.create(1L, requestDto);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("reviewId", "createdAt", "storeId")
                .isEqualTo(expected);
    }

    @Test
    void 리뷰_수정() {

        // Given
        Review mockReturnReview = DumpDataFactory.review();
        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnReview));

        ReviewUpdateRequestDto requestDto = new ReviewUpdateRequestDto("한번 먹은 사람은 없는 맛", 1, "awesome.jpg");
        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "한번 먹은 사람은 없는 맛", 1, "awesome.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        // When
        ReviewResponseDto result = reviewService.update(1L, requestDto);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("reviewId", "createdAt", "storeId")
                .isEqualTo(expected);
    }

    @Test
    void 리뷰_단건_조회() {

        // Given
        Review mockReturnReview = DumpDataFactory.review();

        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "머리카락이 나왔어요.", 5, "someImg.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnReview));

        // When
        ReviewResponseDto result = reviewService.findById(1L);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("reviewId", "createdAt", "storeId")
                .isEqualTo(expected);
    }

    @Test
    void 리뷰_다건_조회() {

        // Given
        Review mockReturnReview = DumpDataFactory.review();

        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "머리카락이 나왔어요.", 5, "someImg.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));

        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnReview));

        // When
        ReviewResponseDto result = reviewService.findById(1L);

        // Then
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("reviewId", "createdAt", "storeId")
                .isEqualTo(expected);
    }

    @Test
    void 없는_ID로_리뷰_단건_조회() {

        // Given
        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // When
        Throwable exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
            reviewService.findById(1L);
        });

        // Then
        assertEquals("404 NOT_FOUND \"No such review id: 1\"", exception.getMessage());
    }
}
