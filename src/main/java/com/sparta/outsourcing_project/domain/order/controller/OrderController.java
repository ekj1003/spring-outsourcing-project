package com.sparta.outsourcing_project.domain.order.controller;

import com.sparta.outsourcing_project.config.anno.InfoAnnotation;
import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @InfoAnnotation
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Auth AuthUser authUser, @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.createOrder(authUser, orderRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOneOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOneOrder(authUser, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder(@Auth AuthUser authUser) {
        List<OrderResponseDto> orders = orderService.getAllOrders(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> patchOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId, @RequestBody OrderPatchRequestDto orderPatchRequestDto){
        OrderResponseDto orderResponseDto = orderService.patchOrder(authUser, orderId, orderPatchRequestDto);
        if(orderResponseDto == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
