package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindReviewIdException;
import com.sparta.outsourcing_project.domain.exception.CannotFindStoreException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.ReviewOwnerCommentRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewOwnerCommentResponseDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.entity.ReviewOwnerComment;
import com.sparta.outsourcing_project.domain.review.repository.ReviewOwnerCommentRepository;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewOwnerService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewOwnerCommentRepository reviewOwnerCommentRepository;

    public List<ReviewResponseDto> getReviews(AuthUser authUser, Long storeId) {
        User user = userRepository.findById(authUser.getId()).orElseThrow();
        Store store = storeRepository.findByIdAndUserId(storeId, user.getId());

        if (store == null) {
            throw new CannotFindStoreException();
        }

        List<Order> orderList = orderRepository.findAllByStoreId(storeId);

        List<Review> reviewList = new ArrayList<>();
        for (Order order : orderList) {
            List<Review> reviews = reviewRepository.findAllByOrderId(order.getId());
            reviewList.addAll(reviews);
        }

        return reviewList.stream().map(ReviewResponseDto::new).toList();
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
