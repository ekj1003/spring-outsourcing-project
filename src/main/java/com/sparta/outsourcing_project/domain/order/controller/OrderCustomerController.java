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

    /**
     * 주문을 생성합니다.
     * 장바구니에 있는 음식을들 주문하기 때문에 다른 값을 받지 않습니다.
     * @param authUser
     * @return OrderResponseDto
     */
    @InfoAnnotation
    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@Auth AuthUser authUser) {
        OrderResponseDto orderResponseDto = orderCustomerService.createOrder(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponseDto);
    }

    /**
     * 주문 한개를 가져옵니다.
     * 주문 다건 조회와 다른점은 주문 상세정보가 출력됩니다.
     * @param authUser
     * @param orderId
     * @return OrderGetOneOrderResponseDto
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetOneOrderResponseDto> getOneOrder(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId) {
        OrderGetOneOrderResponseDto orderGetOneOrderResponseDto = orderCustomerService.getOneOrder(authUser, orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderGetOneOrderResponseDto);
    }

    /**
     * 주문 다건 조회합니다.
     * @param authUser
     * @return List<OrderResponseDto>
     */
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getAllOrder(@Auth AuthUser authUser) {
        List<OrderResponseDto> orders = orderCustomerService.getAllOrders(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(orders);
    }

    /**
     * 주문을 삭제합니다.
     * 삭제를 실패하면 flag에 false값이 들어가 삭제 옵션을 적어달라는 문장이 나옴
     * @param authUser
     * @param orderId
     * @param orderPatchRequestDto
     * @return String
     */
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
