//package com.example.jomajomadelivery.review.service;
//
//import com.example.jomajomadelivery.exception.CustomException;
//import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
//import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
//import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
//import com.example.jomajomadelivery.review.entity.Review;
//import com.example.jomajomadelivery.review.repository.ReviewRepository;
//import com.example.jomajomadelivery.store.entity.Store;
//import com.example.jomajomadelivery.store.repository.StoreRepository;
//import com.example.jomajomadelivery.user.entity.User;
//import com.example.jomajomadelivery.user.repository.UserRepository;
//import com.example.jomajomadelivery.util.DumpDataFactory;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class ReviewServiceTest {
//
//    @Mock
//    ReviewRepository reviewRepository;
//
//    @Mock
//    StoreRepository storeRepository;
//
//    @Mock
//    UserRepository userRepository;
//
//    @InjectMocks
//    ReviewService reviewService;
//
//    @Test
//    void 리뷰_생성() {
//
//        // Given
//        User mockReturnUser = DumpDataFactory.user();
//        Store mockReturnStore = DumpDataFactory.store();
//        Review mockReturnReview = DumpDataFactory.review();
//
//        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto(1L, "머리카락이 나왔어요.", 5, "someImg.jpg");
//        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "머리카락이 나왔어요.", 5, "someImg.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));
//
//            // Mock
//        when(userRepository.existsById(any(Long.class))).thenReturn(true);
//        when(userRepository.getReferenceById(any(Long.class))).thenReturn(mockReturnUser);
//
//        when(storeRepository.existsById(any(Long.class))).thenReturn(true);
//        when(storeRepository.getReferenceById(any(Long.class))).thenReturn(mockReturnStore);
//
//        when(reviewRepository.save(any(Review.class))).thenReturn(mockReturnReview);
//
//
//        // When
//        ReviewResponseDto result = reviewService.create(1L, requestDto);
//
//        // Then
//        assertThat(result)
//                .usingRecursiveComparison()
//                .ignoringFields("reviewId", "createdAt", "storeId")
//                .isEqualTo(expected);
////        verify(reviewRepository, times(1)).deleteById(userId);
//    }
//
//    @Test
//    void 리뷰_수정() {
//        // Given
//
//        Review mockReturnReview = DumpDataFactory.review();
//        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnReview));
//
//        String CONTENTS = "한번 먹은 사람은 없는 맛";
//        String IMG_PATH = "awesome.jpg";
//        ReviewUpdateRequestDto requestDto = new ReviewUpdateRequestDto(CONTENTS, 1, IMG_PATH);
//        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, CONTENTS, 1, IMG_PATH, LocalDateTime.parse("2025-01-09T18:21:44.44634"));
//
//        // When
//        ReviewResponseDto result = reviewService.update(1L, requestDto);
//
//        // Then
//        assertThat(result)
//                .usingRecursiveComparison()
//                .ignoringFields("reviewId", "createdAt", "storeId")
//                .isEqualTo(expected);
//    }
//
////    @Test
////    void 리뷰_다건_조회() {
////
////        // Given
////        Review mockReturnReview = DumpDataFactory.review();
////
////        ReviewResponseDto expected = new ReviewResponseDto(1L, 1L, 1L, "머리카락이 나왔어요.", 5, "someImg.jpg", LocalDateTime.parse("2025-01-09T18:21:44.44634"));
////
////        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.of(mockReturnReview));
////
////        // When
////        Page<ReviewResponseDto> result = reviewService.findById(1L);
////
////        // Then
////        assertThat(result)
////                .usingRecursiveComparison()
////                .ignoringFields("reviewId", "createdAt", "storeId")
////                .isEqualTo(expected);
////    }
//
////    @Test
////    void 없는_ID로_리뷰_단건_조회() {
////
////        // Given
////        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.empty());
////
////        // When
////        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class, () -> {
////            reviewService.findById(1L);
////        });
////
////        // Then
////        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
////    }
//
//    @Test
//    void 없는_ID로_리뷰_수정() {
//
//        // Given
//        ReviewUpdateRequestDto requestDto = new ReviewUpdateRequestDto("적당한 컨텐츠", 3, "properImgPath");
//
//        when(reviewRepository.findById(any(Long.class))).thenReturn(Optional.empty());
//
//        // When
//        CustomException exception = Assertions.assertThrows(CustomException.class, () -> {
//            reviewService.update(1L, requestDto);
//        });
//
//        // Then
////        assertEquals("No such review id: 1", exception.getReason());
//        assertEquals(HttpStatus.NOT_FOUND, exception.getErrorCode().getStatus());
//    }
//}
