package com.sparta.outsourcing_project.domain.order.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long storeId;
    private Long menuId;
    private int quantity;
}
