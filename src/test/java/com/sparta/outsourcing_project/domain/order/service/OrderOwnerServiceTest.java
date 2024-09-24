package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.order.dto.OrderOwnerPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderOwnerServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    StoreRepository storeRepository;

    @InjectMocks
    OrderOwnerService orderOwnerService;

    AuthUser authUser;
    Long userId;
    Long storeId;
    Long orderId;
    User user;
    Store store;
    Order order;

    @BeforeEach
    void setUp() {
        userId = 1L;
        storeId = 5L;
        orderId = 10L;
        authUser = new AuthUser(1L, "a@a.com", UserType.CUSTOMER);
        user = new User("a@a.com", "Aa88888888!", UserType.CUSTOMER);
        store = new Store("음식점", LocalTime.of(0, 0), LocalTime.of(23, 59), 10000, user);
        order = new Order(user, store, 10000);
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(store, "id", 5L);
        ReflectionTestUtils.setField(order, "id", 10L);
    }

    @Nested
    class getAllOrders {
        @Test
        void 전체_조회_성공() {
            // given
            List<Store> storeList = new ArrayList<>();
            List<Order> orderList = new ArrayList<>();
            storeList.add(store);
            orderList.add(order);

            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
            when(storeRepository.findAllByUserId(anyLong())).thenReturn(storeList);
            when(orderRepository.findAllByStoreId(anyLong())).thenReturn(orderList);
            // when
            List<OrderResponseDto> result = orderOwnerService.getAllOrders(authUser);

            // then
            assertNotNull(result);
            assertEquals(5L, result.get(0).getStoreId());
            assertEquals(10L, result.get(0).getOrderId());
        }
    }


    @Test
    void patchOrder() {
        // given
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));
        when(storeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(store));
        when(orderRepository.findByIdAndStoreId(anyLong(), anyLong())).thenReturn(order);

        OrderOwnerPatchRequestDto orderOwnerPatchRequestDto = new OrderOwnerPatchRequestDto("ORDERED");

        // when
        OrderResponseDto result = orderOwnerService.patchOrder(authUser, orderId, storeId, orderOwnerPatchRequestDto);

        // then
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        assertEquals(storeId, result.getStoreId());
        assertEquals(Status.ORDERED, result.getStatus());
    }
}