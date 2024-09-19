package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.store.entity.Store;

import com.sparta.outsourcing_project.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // 로그인 하면 user 자동으로 들어옴
        User user = null;
        // 메뉴 id로 메뉴 찾고, storeid로 store 찾아서 넣어야 함;
        Menu menu = null; // 임시로 사용
        Store store = null; // 임시로 사용

        Order order = new Order(user, menu, store);

        Order saveOrder = orderRepository.save(order);

        OrderResponseDto orderResponseDto = new OrderResponseDto(saveOrder);
        return orderResponseDto;
    }
}
