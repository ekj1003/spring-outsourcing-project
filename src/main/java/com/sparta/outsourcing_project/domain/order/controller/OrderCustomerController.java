package com.sparta.outsourcing_project.domain.order.controller;

import com.sparta.outsourcing_project.config.anno.InfoAnnotation;
import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.order.dto.OrderGetOneOrderResponseDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderPatchRequestDto;
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
    public ResponseEntity<OrderResponseDto> createOrder(@Auth AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderCustomerService.createOrder(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetOneOrderResponseDto> getOneOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId) {
        OrderGetOneOrderResponseDto orderGetOneOrderResponseDto = orderCustomerService.getOneOrder(authUser, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderGetOneOrderResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder(@Auth AuthUser authUser) {
        List<OrderResponseDto> orders = orderCustomerService.getAllOrders(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @InfoAnnotation
    @PatchMapping("/{orderId}")
    public ResponseEntity<String> patchOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId, @RequestBody OrderPatchRequestDto orderPatchRequestDto){
        boolean flag = orderCustomerService.patchOrder(authUser, orderId, orderPatchRequestDto);
        if(flag) {
            return ResponseEntity.status(HttpStatus.OK).body("삭제하였습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제 옵션을 적어주세요");
        }
    }
}
