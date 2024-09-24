//package com.sparta.outsourcing_project.domain.order.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sparta.outsourcing_project.config.authUser.AuthUser;
//import com.sparta.outsourcing_project.config.authUser.AuthUserArgumentResolver;
//import com.sparta.outsourcing_project.domain.order.dto.OrderGetOneOrderResponseDto;
//import com.sparta.outsourcing_project.domain.order.entity.Order;
//import com.sparta.outsourcing_project.domain.order.enums.Status;
//import com.sparta.outsourcing_project.domain.order.service.OrderCustomerService;
//import com.sparta.outsourcing_project.domain.store.entity.Store;
//import com.sparta.outsourcing_project.domain.user.entity.User;
//import com.sparta.outsourcing_project.domain.user.enums.UserType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Nested;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//
//@WebMvcTest(OrderCustomerController.class)
//class OrderCustomerControllerTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//    @MockBean
//    private OrderCustomerService orderCustomerService;
//
//    @MockBean
//    private AuthUserArgumentResolver authUserArgumentResolver;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setUp() {
//        mvc = MockMvcBuilders.standaloneSetup(new OrderCustomerController(orderCustomerService)).setCustomArgumentResolvers(authUserArgumentResolver).build();
//    }
//

//    @Nested
//    class createOrder {
//        @Test
//        void createOrder() {
//            // given
//
//            // when
//
//            //then
//        }
//    }


//    @Test
//    void getOneOrder() {
//        // given
//        AuthUser authUser = new AuthUser(1L, "a@a.com", UserType.CUSTOMER);
//        Long orderId = 1L;
//
//        User user = new User("a@a.com", "Aa88888888!", UserType.CUSTOMER);
//        Store store = new Store("음식점", null, null, 10000, user);
//        Order order = new Order(user, store, 10000);
//        OrderGetOneOrderResponseDto orderGetOneOrderResponseDto = new OrderGetOneOrderResponseDto(order);
//        given(orderCustomerService.getOneOrder(authUser, orderId)).willReturn(orderGetOneOrderResponseDto);
//        // when
//        ResultActions resultActions = mvc.perform(post)
//
//        //then
//    }
//
//    @Test
//    void getAllOrder() {
//    }
//
//    @Test
//    void patchOrder() {
//    }
//}