package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.domain.exception.CannotFindOrderId;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;

import com.sparta.outsourcing_project.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // 로그인 하면 user 자동으로 들어옴
        User user = null;
        // 메뉴 id로 메뉴 찾고, storeid로 store 찾아서 넣어야 함;
        Menu menu = null; // 임시로 사용
        Store store = null; // 임시로 사용

        Order order = new Order(user, menu, store);

        return new OrderResponseDto(orderRepository.save(order));
    }

    public OrderResponseDto getOneOrder(Long orderId) {
        Order findOrder = orderRepository.findById(orderId).orElseThrow(CannotFindOrderId::new);
        if(findOrder.getIsDeleted()){
            throw new CannotFindOrderId();
        }
        return new OrderResponseDto(findOrder);
    }

    public List<OrderResponseDto> getAllOrders() {
        List<Order> findOrders = orderRepository.findAll();
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order findOrder : findOrders) {
            if (!findOrder.getIsDeleted()){
                orderResponseDtoList.add(new OrderResponseDto(findOrder));
            }
        }
        return orderResponseDtoList;
    }

    @Transactional
    public OrderResponseDto patchOrder(Long orderId, OrderPatchRequestDto orderPatchRequestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(CannotFindOrderId::new);

        // ture면 soft delete
        if (orderPatchRequestDto.getIs_deleted()) {
            order.delete();
            reviewRepository.deleteByOrderId(order.getId());
            return null;
        }

        // 메뉴 찾기
        // Menu menu = menuRepository.findByIdw(orderPatchRequestDto.getMenuId());
        // 임시로 사용
        Menu menu = null;
        Order patchOrder = order.patchOrder(menu);
        return new OrderResponseDto(patchOrder);
    }

}
