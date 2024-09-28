package com.sparta.outsourcing_project.domain.order.repository;

import com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderQueryDslRepository {
    List<OrdersCountDto> countOrdersByDate();
    List<OrdersCountDto> countOrdersByMonth();
    List<OrdersPriceDto> getOrdersTotalPriceByDateRange(LocalDateTime start, LocalDateTime end);
    List<OrdersPriceDto> getOrdersTotalPriceDaily();
    List<OrdersPriceDto> getOrdersTotalPriceMonthly();
}
