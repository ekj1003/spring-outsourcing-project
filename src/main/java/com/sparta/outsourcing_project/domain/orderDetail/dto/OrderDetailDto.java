package com.sparta.outsourcing_project.domain.orderDetail.dto;

import com.sparta.outsourcing_project.domain.orderDetail.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class OrderDetailDto {

    private Long orderDetailId;
    private Long menuId;
    private Integer price;
    private Integer quantity;

    public OrderDetailDto(OrderDetail orderDetail) {
        this.orderDetailId = orderDetail.getId();
        this.menuId = orderDetail.getMenu().getId();
        this.price = orderDetail.getPrice();
        this.quantity = orderDetail.getQuantity();
    }

}
