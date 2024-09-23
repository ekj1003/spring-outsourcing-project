package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.*;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;

import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderCustomerService {

    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    public OrderResponseDto createOrder(AuthUser authUser, OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(authUser.getId()).orElseThrow();
        Menu menu = menuRepository.findByIdAndStoreId(orderRequestDto.getMenuId(), orderRequestDto.getStoreId());
        if (menu == null) {
            throw new CannotFindMenuException();
        }
        Store store = storeRepository.findById(orderRequestDto.getStoreId()).orElseThrow(CannotFindStoreException::new);

        // 현재 영업시간인지 확인하기
        if(LocalTime.now().isBefore(store.getOpenAt()) || LocalTime.now().isAfter(store.getCloseAt()) ) {
            throw new NotOpenException();
        }

        // 최저 금액인지 확인하기
        if (menu.getPrice() * orderRequestDto.getQuantity() < store.getMinPrice()) {
            throw new NotMinPriceException(store.getMinPrice() , menu.getPrice() * orderRequestDto.getQuantity());
        }

        int price = menu.getPrice() * orderRequestDto.getQuantity();

        Order order = new Order(user, menu, store, price, orderRequestDto.getQuantity());

        return new OrderResponseDto(orderRepository.save(order));
    }

    public OrderResponseDto getOneOrder(AuthUser authUser, Long orderId) {
        Order findOrder = orderRepository.findByIdAndUserId(orderId, authUser.getId());
        if(findOrder.getIsDeleted()){
            throw new CannotFindOrderException();
        }
        return new OrderResponseDto(findOrder);
    }

    public List<OrderResponseDto> getAllOrders(AuthUser authUser) {
        List<Order> findOrders = orderRepository.findAllByUserId(authUser.getId());
        List<OrderResponseDto> orderResponseDtoList = new ArrayList<>();
        for (Order findOrder : findOrders) {
            if (!findOrder.getIsDeleted()){
                orderResponseDtoList.add(new OrderResponseDto(findOrder));
            }
        }
        return orderResponseDtoList;
    }

    @Transactional
    public OrderResponseDto patchOrder(AuthUser authUser, Long orderId, OrderPatchRequestDto orderPatchRequestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(CannotFindOrderException::new);

        if (authUser.getId() != order.getUser().getId()) {
            throw new UnauthorizedAccessException("작성자가 아닙니다.");
        }

        // ture면 soft delete
        if (orderPatchRequestDto.getIs_deleted()) {
            order.delete();
            reviewRepository.deleteByOrderId(order.getId());
            return null;
        }

        // 메뉴 찾기
        Menu menu = menuRepository.findById(orderPatchRequestDto.getMenuId()).orElseThrow(CannotFindMenuException::new);
        Order patchOrder = order.patchOrder(menu);
        return new OrderResponseDto(patchOrder);
    }

}
