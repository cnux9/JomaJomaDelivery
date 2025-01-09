package com.example.jomajomadelivery.unit.review;

import com.example.jomajomadelivery.store.entity.Category;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import com.example.jomajomadelivery.review.service.ReviewService;
import com.example.jomajomadelivery.user.entity.Role;
import com.example.jomajomadelivery.user.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
public class ReviewServiceTest {
//    @Autowired
    @InjectMocks
    ReviewService reviewService;

    @Mock
    ReviewRepository reviewRepository;

//    @Test
//    void 리뷰_생성() {
//        // Given
//        ReviewCreateRequestDto requestDto = new ReviewCreateRequestDto(1L, "머리카락이 나왔어요.", 5, "someImg.jpg");
//
//        // FIXME
//        User user = newTestUser();
//        Store store = newTestStore(user);
//        Review review = newTestReview(store);
//
//        ReviewResponseDto expectedResult = new ReviewResponseDto();
//
//        when(reviewRepository.save(any(Review.class))).thenReturn(review);
//
//        // When
//        ReviewResponseDto actualResult = reviewService.create(1L, requestDto);
//
//        // Then
//        assertThat(actualResult).isEqualTo(expectedResult);
////        assertsThat
//    }

    private static Review newTestReview(Store store) {
        return Review.builder()
//                .user(user)
                .userId(1L)
                .store(store)
//                .storeId(storeId)
                .contents("머리카락이 나왔어요.")
                .imgPath("someImg.jpg")
                .rating(5)
                .build();
    }

    private static User newTestUser() {
        return User.builder()
                .email("abc@naver.com")
                .password("1111")
                .name("조훈")
                .role(Role.USER)
                .nickName("홍박사")
                .phoneNumber("010-0000-0000")
                .isDeleted(false)
                .build();
    }

    private static Store newTestStore(User user) {
        return Store.builder()
                .user(user)
                .category(Category.JAPANESE)
                .name("파파스터치")
                .description("300년 전통의 가부장적인 맛")
                .address("충주시 사과군")
                .phoneNumber("010-0000-0000")
                .imgPath("someImg.jpg")
                .openTime(LocalTime.NOON)
                .closeTime(LocalTime.MIDNIGHT)
                .minOrderPrice(32100)
                .deliveryPrice(2500)
                .favoriteCount(0L)
                .rating(0.0)
                .isDeleted(false)
                .build();
    }
}
