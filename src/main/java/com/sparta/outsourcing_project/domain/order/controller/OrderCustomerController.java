package com.sparta.outsourcing_project.domain.order.controller;

import com.sparta.outsourcing_project.config.anno.InfoAnnotation;
import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.service.OrderCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/orders")
@RequiredArgsConstructor
public class OrderCustomerController {

    private final OrderCustomerService orderCustomerService;

    @InfoAnnotation
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Auth AuthUser authUser, @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderCustomerService.createOrder(authUser, orderRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOneOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId) {
        OrderResponseDto orderResponseDto = orderCustomerService.getOneOrder(authUser, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder(@Auth AuthUser authUser) {
        List<OrderResponseDto> orders = orderCustomerService.getAllOrders(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @InfoAnnotation
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> patchOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId, @RequestBody OrderPatchRequestDto orderPatchRequestDto){
        OrderResponseDto orderResponseDto = orderCustomerService.patchOrder(authUser, orderId, orderPatchRequestDto);
        if(orderResponseDto == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
