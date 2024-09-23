package com.sparta.outsourcing_project.domain.shoppingCart.dto;

import lombok.Getter;

@Getter
public class ShoppingCartRequestDto {
    private Long storeId;
    private Long menuId;
    private int quantity;
}
