package com.sparta.outsourcing_project.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OrdersPriceDto {
    private String orderDate;
    private long totalPrice;

    public OrdersPriceDto(String orderDate, long totalPrice) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
