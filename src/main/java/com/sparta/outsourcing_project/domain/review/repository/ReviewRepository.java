package com.sparta.outsourcing_project.domain.review.repository;

import com.sparta.outsourcing_project.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    void deleteByOrderId(Long orderId);

    List<Review> findAllByUserId(Long userId);

    List<Review> findAllByOrderId(Long id);
}
