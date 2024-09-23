package com.sparta.outsourcing_project.domain.order.dto;

import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.orderDetail.dto.OrderDetailDto;
import com.sparta.outsourcing_project.domain.orderDetail.entity.OrderDetail;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderGetOneOrderResponseDto {

    private Long orderId;
    private Long storeId;
    private Integer totalPrice;
    private Status status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private List<OrderDetailDto> orderDetailList;

    public OrderGetOneOrderResponseDto(Order saveOrder) {
        this.orderId = saveOrder.getId();
        this.storeId = saveOrder.getStore().getId();
        this.totalPrice = saveOrder.getTotalPrice();
        this.status = saveOrder.getStatus();
        this.createAt = saveOrder.getCreatedAt();
        this.updateAt = saveOrder.getUpdatedAt();
        this.orderDetailList = saveOrder.getOrderDetailList().stream().map(OrderDetailDto::new).toList();
    }
}
