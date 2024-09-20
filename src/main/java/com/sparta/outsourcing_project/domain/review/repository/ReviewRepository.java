package com.sparta.outsourcing_project.domain.review.repository;

import com.sparta.outsourcing_project.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteByOrderId(Long orderId);
}
