package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.domain.exception.CannotFindOrderId;
import com.sparta.outsourcing_project.domain.exception.CannotFindReviewId;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;

    public ReviewResponseDto createReview(Long orderId, ReviewRequestDto reviewRequestDto) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(CannotFindOrderId::new);
        if(findOrder.getIsDeleted()) {
            throw new CannotFindOrderId();
        }
        return new ReviewResponseDto(reviewRepository.save(new Review(findOrder, reviewRequestDto)));
    }

    public List<ReviewResponseDto> getReviews() {
        return reviewRepository.findAll().stream().map(ReviewResponseDto::new).toList();
    }

    @Transactional
    public ReviewResponseDto patchReview(Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(CannotFindReviewId::new);
        Review review = findReview.patchReview(reviewRequestDto);
        return new ReviewResponseDto(review);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(CannotFindReviewId::new);
        reviewRepository.delete(findReview);
    }
}
