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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrdersService ordersService;


    @Transactional
    public ReviewResponseDto create(Long storeId, Long userId, ReviewCreateRequestDto dto) {
        validateRatingAndContents(dto.rating(), dto.contents());

        Order foundOrder = ordersService.getById(dto.orderId());
        if (!foundOrder.getStatus().equals(Status.DELIVERED)) {
            throw new CustomException(ReviewErrorCode.ORDER_NOT_COMPLETE);
        }

        if (!storeRepository.existsById(storeId)) {
            throw new CustomException(StoreErrorCode.STORE_NOT_FOUND);
        }
        Store foundStore = storeRepository.findById(storeId).orElseThrow(() -> new CustomException(StoreErrorCode.STORE_NOT_FOUND));

        User userRef = userRepository.getReferenceById(userId);
        Review newReview = Review.createReview(userRef, foundStore, foundOrder, dto);
        Review savedReview = reviewRepository.save(newReview);

        Double averageRating = reviewRepository.getAverageRatingByStoreId(storeId);
        foundStore.updateRating(averageRating);

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
    public void delete(Long reviewId, Long userId) {


        if (!reviewRepository.existsById(reviewId)) {
            throw new CustomException(ReviewErrorCode.REVIEW_NOT_FOUND);
        }
        reviewRepository.deleteById(reviewId);
    }
}