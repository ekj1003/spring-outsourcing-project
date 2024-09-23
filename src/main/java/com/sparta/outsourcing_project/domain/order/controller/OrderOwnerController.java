package com.sparta.outsourcing_project.domain.order.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.order.dto.OrderOwnerPatchRequestDto;
import com.sparta.outsourcing_project.domain.order.dto.OrderResponseDto;
import com.sparta.outsourcing_project.domain.order.service.OrderOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners/orders")
@RequiredArgsConstructor
public class OrderOwnerController {

    private final OrderOwnerService orderOwnerService;

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder(@Auth AuthUser authUser) {
        List<OrderResponseDto> orders = orderOwnerService.getAllOrders(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    @PatchMapping("/{orderId}/stores/{storeId}")
    public ResponseEntity<OrderResponseDto> patchOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId, @PathVariable("storeId") Long storeId, @RequestBody OrderOwnerPatchRequestDto orderOwnerPatchRequestDto){
        OrderResponseDto orderResponseDto = orderOwnerService.patchOrder(authUser, orderId, storeId, orderOwnerPatchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }
}
