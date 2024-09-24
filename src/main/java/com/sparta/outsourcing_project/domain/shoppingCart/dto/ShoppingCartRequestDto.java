package com.sparta.outsourcing_project.domain.shoppingCart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ShoppingCartRequestDto {
    private Long storeId;
    private Long menuId;
    private int quantity;

    public ShoppingCartRequestDto(Long storeId, Long menuId, int quantity) {
        this.storeId = storeId;
        this.menuId = menuId;
        this.quantity = quantity;
    }
}
