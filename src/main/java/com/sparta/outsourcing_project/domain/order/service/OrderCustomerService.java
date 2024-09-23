package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.*;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.order.dto.OrderGetOneOrderResponseDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.orderDetail.entity.OrderDetail;
import com.sparta.outsourcing_project.domain.orderDetail.repository.OrderDetailRepository;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.shoppingCart.repository.ShoppingCartRepository;
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
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional
    public OrderResponseDto createOrder(AuthUser authUser) {
        // 장바구니에서 목록 불러오기
        List<ShoppingCart> shoppingCartList = shoppingCartRepository.findAllByUserId(authUser.getId());

        if (shoppingCartList.isEmpty()) {
            throw new CannotFindShoppingCartException();
        }

        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));
        Store store = shoppingCartList.get(0).getStore();

        // 현재 영업시간인지 확인하기
        if(LocalTime.now().isBefore(store.getOpenAt()) || LocalTime.now().isAfter(store.getCloseAt()) ) {
            throw new NotOpenException();
        }

        int totalPrice = 0;

        for (ShoppingCart shoppingCart : shoppingCartList) {
            totalPrice += shoppingCart.getPrice() * shoppingCart.getQuantity();
        }

        // 최저 금액인지 확인하기
        if (totalPrice < store.getMinPrice()) {
            throw new NotMinPriceException(store.getMinPrice() , totalPrice);
        }

        // 주문 생성하기
        Order order = new Order(user, store, totalPrice);

        // 주문 상세 생성하기
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShoppingCart shoppingCart : shoppingCartList) {
            orderDetailList.add(new OrderDetail(shoppingCart, order));
        }

        // 주문에 주문 상세 저장하기
        order.updateOrderDetailList(orderDetailList);

        for (OrderDetail orderDetail : order.getOrderDetailList()) {
            System.out.println(orderDetail.getMenu().getName());
        }

        // 주문 상태
        order.updateStatus(Status.ORDERED);

        // 장바구니 삭제하기
        shoppingCartRepository.deleteAllByUserId(authUser.getId());

        // 주문 상세 저장
        orderDetailRepository.saveAll(orderDetailList);
        return new OrderResponseDto(orderRepository.save(order));
    }

    public OrderGetOneOrderResponseDto getOneOrder(AuthUser authUser, Long orderId) {
        Order findOrder = orderRepository.findByIdAndUserId(orderId, authUser.getId());
        if(findOrder.getIsDeleted()){
            throw new CannotFindOrderException();
        }
        return new OrderGetOneOrderResponseDto(findOrder);
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
    public boolean patchOrder(AuthUser authUser, Long orderId, OrderPatchRequestDto orderPatchRequestDto) {
        Order order = orderRepository.findById(orderId).orElseThrow(CannotFindOrderException::new);

        if (authUser.getId() != order.getUser().getId()) {
            throw new UnauthorizedAccessException("작성자가 아닙니다.");
        }

        if (order.getIsDeleted()) {
            throw new CannotFindOrderException();
        }

        // ture면 soft delete
        if (orderPatchRequestDto.getIs_deleted()) {
            order.delete();
            reviewRepository.deleteByOrderId(order.getId());
            return true;
        }
        return false;
    }

}
