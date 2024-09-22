package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindOrderIdException;
import com.sparta.outsourcing_project.domain.exception.CannotFindReviewIdException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.request.ReviewOwnerCommentRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.request.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.response.ReviewOwnerCommentResponseDto;
import com.sparta.outsourcing_project.domain.review.dto.response.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.entity.ReviewOwnerComment;
import com.sparta.outsourcing_project.domain.review.repository.ReviewOwnerCommentRepository;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ReviewOwnerCommentRepository reviewOwnerCommentRepository;

    public ReviewResponseDto createReview(AuthUser authUser, Long orderId, ReviewRequestDto reviewRequestDto) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(CannotFindOrderIdException::new);
        User findUser = userRepository.findById(authUser.getId()).orElseThrow();
        if(findOrder.getIsDeleted()) {
            throw new CannotFindOrderIdException();
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

    public ReviewOwnerCommentResponseDto saveOwnerComment(AuthUser authUser, Long reviewId, ReviewOwnerCommentRequestDto reviewOwnerCommentRequestDto) {
        User user = userRepository.findById(authUser.getId())
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(CannotFindReviewIdException::new);

        if(!user.getId().equals(review.getOrder().getStore().getUser().getId())) {
            // 가게 주인(User) -> 가게(Store) -> 주문(Order) -> 리뷰(Review)이기 때문에
            // authUser의 아이디와 해당 리뷰가 달린 가게의 사장님 유저의 아이디와 같은지 확인해야한다.
            throw new UnauthorizedAccessException("이 리뷰에 답글을 달 권한이 없습니다.");
        }

        ReviewOwnerComment reviewOwnerComment = new ReviewOwnerComment(review, reviewOwnerCommentRequestDto.getOwnerComment());
        reviewOwnerCommentRepository.save(reviewOwnerComment);
        return new ReviewOwnerCommentResponseDto(reviewOwnerComment);
//        return reviewOwnerComment.getId();

    }
}
