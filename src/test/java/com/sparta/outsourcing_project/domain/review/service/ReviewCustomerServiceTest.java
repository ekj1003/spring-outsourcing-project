package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewCustomerServiceTest {

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    ReviewCustomerService reviewCustomerService;

    Long userId;
    Long storeId;
    Long orderId;
//    Long menuId;

    AuthUser authUser;
    User user;
    Store store;
    Order order;
//    Menu menu;

    @BeforeEach
    void setUp() {
        userId = 1L;
        storeId = 5L;
        orderId = 10L;
//        menuId = 15L;

        authUser = new AuthUser(1L, "a@a.com", UserType.CUSTOMER);
        user = new User("a@a.com", "Aa88888888!", UserType.CUSTOMER);
        store = new Store("음식점", LocalTime.of(0, 0), LocalTime.of(23, 59), 10000, user);
        order = new Order(user, store, 10000);
//        menu = new Menu(store, MenuType.KOREAN, "음식", 10000, "맛있음");

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(store, "id", storeId);
        ReflectionTestUtils.setField(order, "id", orderId);
//        ReflectionTestUtils.setField(menu, "id", menuId);
    }

    @Test
    void createReview() {
        // given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto("content", 3);

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(order));
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        order.updateStatus(Status.COMPLETED);

        Review review = new Review(user, order, reviewRequestDto);
        given(reviewRepository.save(any(Review.class))).willReturn(review);


        // when
        ReviewResponseDto result = reviewCustomerService.createReview(authUser, orderId, reviewRequestDto);

        // then
        assertNotNull(result);
        assertEquals(review.getId(), result.getId());
        assertEquals(review.getStar(), result.getStar());

    }

    @Test
    void getReviews() {
        // given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto("content", 3);
        Long reviewId = 100L;
        Review review = new Review(user, order, reviewRequestDto);
        ReflectionTestUtils.setField(review, "id", reviewId);

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(review);
        given(reviewRepository.findAllByUserId(anyLong())).willReturn(reviewList);

        // when
        List<ReviewResponseDto> result = reviewCustomerService.getReviews(authUser);

        // then
        assertNotNull(result);
        assertEquals(review.getId(), result.get(0).getId());
        assertEquals(review.getStar(), result.get(0).getStar());
        assertEquals(review.getContent(), result.get(0).getContent());

    }

    @Test
    void patchReview() {
        // given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto("content", 3);

        Long reviewId = 100L;
        Review review = new Review(user, order, reviewRequestDto);
        ReflectionTestUtils.setField(review, "id", reviewId);
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        // when
        ReviewResponseDto result = reviewCustomerService.patchReview(authUser, reviewId, reviewRequestDto);

        // then
        assertNotNull(result);
    }

    @Test
    void deleteReview() {
        // given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto("content", 3);

        Long reviewId = 100L;
        Review review = new Review(user, order, reviewRequestDto);
        ReflectionTestUtils.setField(review, "id", reviewId);
        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));

        // when
        reviewCustomerService.deleteReview(authUser, reviewId);

        // then
        verify(reviewRepository, times(1)).delete(any(Review.class));
    }
}