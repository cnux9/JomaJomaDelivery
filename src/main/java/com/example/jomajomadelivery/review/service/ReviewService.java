package com.example.jomajomadelivery.review.service;

import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.orders.entity.Order;
import com.example.jomajomadelivery.orders.entity.Status;
import com.example.jomajomadelivery.orders.service.OrdersService;
import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.exception.ReviewErrorCode;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.exception.StoreErrorCode;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.entity.User;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrdersService ordersService;


    @Transactional
    public ReviewResponseDto create(Long storeId, ReviewCreateRequestDto dto) {
        validateRatingAndContents(dto.rating(), dto.contents());

        // 현재 로그인된 유저 -> 리뷰 작성자
        // FIXME: 실제 로그인된 userId값으로 대체
        Long userId = 1L;

        Order foundOrder = ordersService.getById(dto.orderId());
        if (!foundOrder.getStatus().equals(Status.DELIVERED)) {
            throw new CustomException(ReviewErrorCode.ORDER_NOT_COMPLETE);
        }

        // 로그인된 사용자로 부터 가져온 ID인데 무슨 경우?
        if (!userRepository.existsById(userId)) {
            // FIXME: user 쪽 예외로 대체
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user id.");
//            throw new CustomException(UserErrorCode.USER_NOT_FOUND);
        }
        User userRef = userRepository.getReferenceById(storeId);

        if (!storeRepository.existsById(storeId)) {
            throw new CustomException(StoreErrorCode.STORE_NOT_FOUND);
        }
        Store storeRef = storeRepository.getReferenceById(storeId);


        Review newReview = Review.createReview(userRef, storeRef, foundOrder, dto);
        Review savedReview = reviewRepository.save(newReview);

        return ReviewResponseDto.toDto(savedReview);
    }

    public Page<ReviewResponseDto> findAllById(Long storeId, Integer minRating, Integer maxRating, Pageable pageable) {
        return reviewRepository.findByStoreId(storeId, minRating, maxRating, pageable);
    }

    @Transactional
    public ReviewResponseDto update(Long id, ReviewUpdateRequestDto dto) {
        validateRatingAndContents(dto.rating(), dto.contents());
        Review foundReview = getById(id);
        foundReview.update(dto);
        return ReviewResponseDto.toDto(foundReview);
    }

    private Review getById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND));
    }

    private static void validateRatingAndContents(Integer rating, String contents) {
        if (contents.length()>200) {
            throw new CustomException(ReviewErrorCode.CONTENTS_LENGTH_NOT_VALID);
        }

        if (rating<1 || 5< rating) {
            throw new CustomException(ReviewErrorCode.RATING_NOT_VALID);
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(id);
    }
}
