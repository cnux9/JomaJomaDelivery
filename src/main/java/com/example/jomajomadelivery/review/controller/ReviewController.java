package com.example.jomajomadelivery.review.controller;

import com.example.jomajomadelivery.review.dto.request.ReviewCreateRequestDto;
import com.example.jomajomadelivery.review.dto.request.ReviewUpdateRequestDto;
import com.example.jomajomadelivery.review.dto.response.ReviewResponseDto;
import com.example.jomajomadelivery.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores/{storeId}/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@PathVariable Long storeId, @RequestBody ReviewCreateRequestDto dto) {
        return new ResponseEntity<>(reviewService.create(storeId, dto), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ReviewResponseDto> findById(@PathVariable Long userId) {
        return new ResponseEntity<>(reviewService.findById(userId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<ReviewResponseDto>> findAllById(@PathVariable Long storeId, Pageable pageable) {
        return new ResponseEntity<>(reviewService.findAllById(storeId, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> update(@PathVariable Long id, @RequestBody ReviewUpdateRequestDto dto) {
        return new ResponseEntity<>(reviewService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
