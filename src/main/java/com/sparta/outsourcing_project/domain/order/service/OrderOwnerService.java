package com.sparta.outsourcing_project.domain.order.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.CannotFindOrderException;
import com.sparta.outsourcing_project.domain.exception.CannotFindStoreException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.order.dto.OrderOwnerPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderOwnerService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    public List<OrderResponseDto> getAllOrders(AuthUser authUser) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));
        List<Store> storeList = storeRepository.findAllByUserId(user.getId());
        List<Order> allOrderList = new ArrayList<>();
        for (Store store : storeList) {
            List<Order> orderList = orderRepository.findAllByStoreId(store.getId());
            allOrderList.addAll(orderList);
        }

        return allOrderList.stream().map(OrderResponseDto::new).toList();
    }

    @Transactional
    public OrderResponseDto patchOrder(AuthUser authUser, Long orderId, Long storeId, OrderOwnerPatchRequestDto orderOwnerPatchRequestDto) {
        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(storeId).orElseThrow(CannotFindStoreException::new);
        if (store.getUser().getId() != user.getId()) {
            throw new CannotFindStoreException();
        }
        Order order = orderRepository.findByIdAndStoreId(orderId, storeId);
        if (order == null) {
            throw new CannotFindOrderException();
        }

        Order patchOrder = order.updateStatus(Status.of(orderOwnerPatchRequestDto.getStatus()));

        return new OrderResponseDto(patchOrder);
    }
}
