package com.sparta.outsourcing_project.domain.shoppingCart.dto;

import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import lombok.Getter;

@Getter
public class ShoppingCartResponseDto {

    private Long shoppingCartId;
    private Long storeId;
    private Long menuId;
    private int quantity;
    private int price;

    public ShoppingCartResponseDto(ShoppingCart shoppingCart) {
        shoppingCartId = shoppingCart.getId();
        storeId = shoppingCart.getStore().getId();
        menuId = shoppingCart.getMenu().getId();
        quantity = shoppingCart.getQuantity();
        price = shoppingCart.getPrice();
    }
}
