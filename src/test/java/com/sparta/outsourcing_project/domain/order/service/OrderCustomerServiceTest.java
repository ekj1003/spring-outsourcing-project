package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindOrderException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.order.controller.OrderCustomerController;
import com.sparta.outsourcing_project.domain.order.dto.OrderGetOneOrderResponseDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.orderDetail.repository.OrderDetailRepository;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.shoppingCart.repository.ShoppingCartRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCustomerServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    ReviewRepository reviewRepository;
    
    @Mock
    UserRepository userRepository;

    @Mock
    ShoppingCartRepository shoppingCartRepository;
    
    @Mock
    OrderDetailRepository orderDetailRepository;

    @InjectMocks
    OrderCustomerService orderCustomerService;


    AuthUser authUser;
    Long orderId;
    User user;
    Store store;
    Order order;

    @BeforeEach
    void setUp() {
        authUser = new AuthUser(1L, "a@a.com", UserType.CUSTOMER);
        orderId = 10L;
        user = new User("a@a.com", "Aa88888888!", UserType.CUSTOMER);
        store = new Store("음식점", LocalTime.of(0, 0), LocalTime.of(23, 59), 10000, user);
        order = new Order(user, store, 10000);
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(store, "id", 5L);
        ReflectionTestUtils.setField(order, "id", 10L);
    }

    @Nested
    class createOrder {

        @Test
        void 주문_성공() {
            // given
            Menu menu = new Menu(store, MenuType.KOREAN, "차", 5000, "맛있음");
            ShoppingCart shoppingCart = new ShoppingCart(user, store, menu, 10, 10000);
            List<ShoppingCart> shoppingCartList = new ArrayList<>();
            shoppingCartList.add(shoppingCart);
            when(shoppingCartRepository.findAllByUserId(anyLong())).thenReturn(shoppingCartList);

            when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

            doNothing().when(shoppingCartRepository).deleteAllByUserId(anyLong());

            when(orderDetailRepository.saveAll(anyList())).thenReturn(null);

            when(orderRepository.save(any(Order.class))).thenReturn(order);
            OrderResponseDto orderResponseDto = new OrderResponseDto(order);

            // when
            OrderResponseDto result = orderCustomerService.createOrder(authUser);

            // then
            assertNotNull(result);
        }

    }

    @Nested
    class getOneOrder {
        @Test
        void 주문이_없는_경우_예외() {
            //given

            // when
            when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(null);

            // then
            // 예외가 발생해야 함
            CannotFindOrderException cannotFindOrderException = assertThrows(CannotFindOrderException.class, () -> {
                orderCustomerService.getOneOrder(authUser, orderId);
            });
        }

        @Test
        void 주문이_삭제된_경우_예외() {
            // given
            order.delete();

            // when
            // 주문은 존재하지만 삭제됨
            when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(order);

            //then
            // 예외가 발생해야 함
            CannotFindOrderException cannotFindOrderException = assertThrows(CannotFindOrderException.class, () -> {
                orderCustomerService.getOneOrder(authUser, orderId);
            });
        }

        @Test
        void 주문이_조회_성공() {
            // givn
            when(orderRepository.findByIdAndUserId(anyLong(), anyLong())).thenReturn(order);

            // when
            OrderGetOneOrderResponseDto result = orderCustomerService.getOneOrder(authUser, orderId);

            // then
            assertNotNull(result);
            assertEquals(5L, result.getStoreId());
            assertEquals(10L, result.getOrderId());

        }
    }


    @Nested
    class getAllOrders {
        @Test
        void 다건_조회_성공() {
            // given
            List<Order> orderList = new ArrayList<>();
            orderList.add(order);
            when(orderRepository.findAllByUserId(anyLong())).thenReturn(orderList);

            // when
            List<OrderResponseDto> result = orderCustomerService.getAllOrders(authUser);

            // then
            assertNotNull(result);
            assertEquals(5L, result.get(0).getStoreId());
            assertEquals(10L, result.get(0).getOrderId());

        }
    }

    @Nested
    class patchOrder {
        @Test
        void 작성자가_아닌경우_예외() {
            // given
            OrderPatchRequestDto orderPatchRequestDto = new OrderPatchRequestDto(true);
            when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
            ReflectionTestUtils.setField(user, "id", 2L);

            // when
            UnauthorizedAccessException result = assertThrows(UnauthorizedAccessException.class, () -> orderCustomerService.patchOrder(authUser, orderId, orderPatchRequestDto));

            // then
            assertEquals("작성자가 아닙니다.", result.getMessage());
        }

        @Test
        void 삭제_성공() {
            // given
            OrderPatchRequestDto orderPatchRequestDto = new OrderPatchRequestDto(true);
            when(orderRepository.findById(anyLong())).thenReturn(Optional.ofNullable(order));
            doNothing().when(reviewRepository).deleteByOrderId(anyLong());

            // when
            boolean result = orderCustomerService.patchOrder(authUser, orderId, orderPatchRequestDto);

            // then
            assertEquals(true, result);
        }
    }
}