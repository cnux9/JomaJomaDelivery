package com.example.jomajomadelivery.review.repository;

import com.example.jomajomadelivery.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
