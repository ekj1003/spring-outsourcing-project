package com.sparta.outsourcing_project.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OrdersPriceDto {
    private String orderDate;
    private Integer totalPrice;

    public OrdersPriceDto(String orderDate, Integer totalPrice) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
    }
}
