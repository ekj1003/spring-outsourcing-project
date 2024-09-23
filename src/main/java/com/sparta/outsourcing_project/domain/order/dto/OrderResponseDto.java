package com.sparta.outsourcing_project.domain.order.dto;

import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderResponseDto {

    private Long orderId;
    private Long storeId;
    private Integer totalPrice;
    private Status status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public OrderResponseDto(Order saveOrder) {
        this.orderId = saveOrder.getId();
        this.storeId = saveOrder.getStore().getId();
        this.totalPrice = saveOrder.getTotalPrice();
        this.status = saveOrder.getStatus();
        this.createAt = saveOrder.getCreatedAt();
        this.updateAt = saveOrder.getUpdatedAt();
    }
}
