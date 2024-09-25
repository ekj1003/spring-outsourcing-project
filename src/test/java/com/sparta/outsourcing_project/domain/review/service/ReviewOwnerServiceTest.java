package com.sparta.outsourcing_project.domain.review.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.repository.ReviewOwnerCommentRepository;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class ReviewOwnerServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    ReviewOwnerCommentRepository reviewOwnerCommentRepository;

    @InjectMocks
    ReviewOwnerService reviewOwnerService;

    Long userId;
    Long storeId;
    Long orderId;
    Long menuId;

    AuthUser authUser;
    User user;
    Store store;
    Order order;
    Menu menu;

    @BeforeEach
    void setUp() {
        userId = 1L;
        storeId = 5L;
        orderId = 10L;
        menuId = 15L;

        authUser = new AuthUser(1L, "a@a.com", UserType.CUSTOMER);
        user = new User("a@a.com", "Aa88888888!", UserType.CUSTOMER);
        store = new Store("음식점", LocalTime.of(0, 0), LocalTime.of(23, 59), 10000, user);
        order = new Order(user, store, 10000);
        menu = new Menu(store, MenuType.KOREAN, "음식", 10000, "맛있음");

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(store, "id", storeId);
        ReflectionTestUtils.setField(order, "id", orderId);
        ReflectionTestUtils.setField(menu, "id", menuId);
    }

    @Test
    void getReviews() {
        // given
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(storeRepository.findByIdAndUserId(anyLong(), anyLong())).willReturn(store);

        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        given(orderRepository.findAllByStoreId(anyLong())).willReturn(orderList);

        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(user, order, new ReviewRequestDto("content", 3)));
        given(reviewRepository.findAllByOrderId(anyLong())).willReturn(reviewList);


        // when
        List<ReviewResponseDto> result = reviewOwnerService.getReviews(authUser, storeId);

        // then
        assertNotNull(result);
        assertEquals(reviewList.get(0).getId(), result.get(0).getId());
        assertEquals(reviewList.get(0).getStar(), result.get(0).getStar());
    }

//    @Test
//    void saveOwnerComment() {
//        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
//        Review review = new Review(user, order, new ReviewRequestDto("content", 3));
//        given(reviewRepository.findById(anyLong())).willReturn(Optional.of(review));
//
//
//    }
}