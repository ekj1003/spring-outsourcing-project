package com.sparta.outsourcing_project.domain.order.controller;

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

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOneOrder(@PathVariable("orderId") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOneOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder() {
        List<OrderResponseDto> orders = orderService.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> patchOrder(@PathVariable("orderId") Long orderId, @RequestBody OrderPatchRequestDto orderPatchRequestDto){
        OrderResponseDto orderResponseDto = orderService.patchOrder(orderId, orderPatchRequestDto);
        if(orderResponseDto == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
