package com.example.jomajomadelivery.review.service;

import com.example.jomajomadelivery.exception.CustomException;
import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.exception.ReviewErrorCode;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import com.example.jomajomadelivery.store.entity.Store;
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


    @Transactional
    public ReviewResponseDto create(Long storeId, ReviewCreateRequestDto dto) {
        if (dto.rating()<1 || 5<dto.rating()) {
            throw new CustomException(ReviewErrorCode.RATING_NOT_VALID);
        }

        if (dto.contents().length()>200) {
            throw new CustomException(ReviewErrorCode.CONTENTS_LENGTH_NOT_VALID);
        }

        // 현재 로그인된 유저 -> 리뷰 작성자
        // FIXME: 실제 로그인된 userId값으로 대체
        Long userId = 1L;

        // 로그인된 사용자로 부터 가져온 ID인데 무슨 경우?
        if (!userRepository.existsById(userId)) {
            // FIXME: user 쪽 예외로 대체
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user id.");
        }
        User userRef = userRepository.getReferenceById(storeId);

        if (!storeRepository.existsById(storeId)) {
            // FIXME: store 쪽 예외로 대체
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such store id.");
        }
        Store storeRef = storeRepository.getReferenceById(storeId);

        Review review = Review.createReview(userRef, storeRef , dto);
        Review savedReview = reviewRepository.save(review);

        return ReviewResponseDto.toDto(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto findById(Long id) {
        Review foundReview = reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such review id: " + id));
        return ReviewResponseDto.toDto(foundReview);
    }

    public Page<ReviewResponseDto> findAllById(Long storeId, Pageable pageable) {
        return reviewRepository.findByStoreId(storeId, pageable);
    }

    @Transactional
    public ReviewResponseDto update(Long id, ReviewUpdateRequestDto dto) {
        if (dto.rating()<1 || 5<dto.rating()) {
            throw new CustomException(ReviewErrorCode.RATING_NOT_VALID);
        }

        if (dto.contents().length()>200) {
            throw new CustomException(ReviewErrorCode.CONTENTS_LENGTH_NOT_VALID);
        }

        Review foundReview = reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such review id: " + id));
        foundReview.update(dto);
        return ReviewResponseDto.toDto(foundReview);
    }

    @Transactional
    public void delete(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such review id: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
