package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindStoreException;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
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
}
