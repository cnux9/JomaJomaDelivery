package com.example.jomajomadelivery.review.service;

import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.entity.Review;
import com.example.jomajomadelivery.review.repository.ReviewRepository;
import com.example.jomajomadelivery.store.entity.Store;
import com.example.jomajomadelivery.store.repository.StoreRepository;
import com.example.jomajomadelivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
//        User foundUser = userRepository.getReferenceById(dto.uesrId());
        // 현재 로그인된 유저 -> 리뷰 작성자
        // FIXME:

        Store foundStore = storeRepository.getReferenceById(storeId);

//        Review review = Review.createReview(foundUser, foundStore, dto);
        Review review = Review.createReview(1L, foundStore, dto);
        Review savedReview = reviewRepository.save(review);

        return ReviewResponseDto.toDto(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewResponseDto findById(Long id) {
        Review foundReview = reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such review id: " + id));
        return ReviewResponseDto.toDto(foundReview);
    }

    @Transactional
    public ReviewResponseDto update(Long id, ReviewUpdateRequestDto dto) {
        Review foundReview = reviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such review id: " + id));
        foundReview.update(dto);
        return ReviewResponseDto.toDto(foundReview);
    }

    @Transactional
    public void delete(Long id) {
        // TODO: not found 에러 처리?
        reviewRepository.deleteById(id);
    }

//    public Page<ReviewResponseDto> findAllById(Long storeId, Pageable pageable) {
//        return reviewRepository.findByStoreId(storeId, pageable);
//    }
}
