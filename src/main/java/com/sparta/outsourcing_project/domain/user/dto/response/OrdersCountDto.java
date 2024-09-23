package com.sparta.outsourcing_project.domain.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrdersCountDto {
    private String orderDate;
    private long totalOrderCount;

    public OrdersCountDto(String orderDate, long totalOrderCount) {
        this.orderDate = orderDate;
        this.totalOrderCount = totalOrderCount;
    }
}
