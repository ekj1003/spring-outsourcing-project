package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindOrderException;
import com.sparta.outsourcing_project.domain.exception.CannotFindReviewIdException;
import com.sparta.outsourcing_project.domain.exception.NotArrivedException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewCustomerService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;


    public ReviewResponseDto createReview(AuthUser authUser, Long orderId, ReviewRequestDto reviewRequestDto) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(CannotFindOrderException::new);
        User findUser = userRepository.findById(authUser.getId()).orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        if(findOrder.getIsDeleted()) {
            throw new CannotFindOrderException();
        }

        if (findOrder.getStatus() != Status.COMPLETED) {
            throw new NotArrivedException();
        }

        if (findOrder.getUser().getId() != findUser.getId()) {
            throw new CannotFindOrderException();
        }

        return new ReviewResponseDto(reviewRepository.save(new Review(findUser, findOrder, reviewRequestDto)));
    }

    public List<ReviewResponseDto> getReviews(AuthUser authUser) {
        return reviewRepository.findAllByUserId(authUser.getId()).stream().map(ReviewResponseDto::new).toList();
    }

    @Transactional
    public ReviewResponseDto patchReview(AuthUser authUser, Long reviewId, ReviewRequestDto reviewRequestDto) {
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(CannotFindReviewIdException::new);

        if (authUser.getId() != findReview.getUser().getId()) {
            throw new UnauthorizedAccessException("작성자가 아닙니다.");
        }

        Review review = findReview.patchReview(reviewRequestDto);
        return new ReviewResponseDto(review);
    }

    @Transactional
    public void deleteReview(AuthUser authUser, Long reviewId) {
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(CannotFindReviewIdException::new);

        if (authUser.getId() != findReview.getUser().getId()) {
            throw new UnauthorizedAccessException("작성자가 아닙니다.");
        }

        reviewRepository.delete(findReview);
    }


}
