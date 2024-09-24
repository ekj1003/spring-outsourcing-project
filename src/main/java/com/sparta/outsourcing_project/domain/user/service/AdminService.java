package com.sparta.outsourcing_project.domain.user.service;

import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.user.dto.response.*;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public Page<AdminUsersDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(AdminUsersDto::new);
    }


    public OrdersCountDto getOrdersCountByDate(LocalDate date) {
        LocalDateTime startOfDate = date.atStartOfDay();
        LocalDateTime endOfDate = startOfDate.plusDays(1);
        Long count = orderRepository.countByCreatedAtBetween(startOfDate, endOfDate);
        return new OrdersCountDto(date.toString(), count);
    }

    public List<OrderCountDtoInterface> getOrdersCountedDaily() {
        return orderRepository.countOrdersByDate();
    }

    public OrdersCountDto getOrdersCountByMonth(YearMonth date) {
        LocalDateTime startOfDate = date.atDay(1).atStartOfDay();
        LocalDateTime endOfDate = date.atEndOfMonth().atTime(23, 59, 59);
        Long count = orderRepository.countByCreatedAtBetween(startOfDate, endOfDate);
        return new OrdersCountDto(date.toString(), count);
    }

    public List<OrderCountDtoInterface> getOrdersCountedMonthly() {
        return orderRepository.countOrdersByMonth();
    }

    public OrderPriceDtoInterface getOrdersTotalPriceByDate(LocalDate date) {
        LocalDateTime startOfDate = date.atStartOfDay();
        LocalDateTime endOfDate = startOfDate.plusDays(1);
        List<OrderPriceDtoInterface> list = orderRepository.getOrdersTotalPriceByDateRange(startOfDate, endOfDate);
        if(list.isEmpty()) return null;
        return list.get(0);
    }

    public List<OrderPriceDtoInterface> getOrdersTotalPriceDaily() {
        return orderRepository.getOrdersTotalPriceDaily();
    }

    public OrderPriceDtoInterface getOrdersTotalPriceByMonth(YearMonth date) {
        LocalDateTime startOfDate = date.atDay(1).atStartOfDay();
        LocalDateTime endOfDate = date.atEndOfMonth().atTime(23, 59, 59);
        List<OrderPriceDtoInterface> list = orderRepository.getOrdersTotalPriceByDateRange(startOfDate, endOfDate);
        if(list.isEmpty()) return null;
        return list.get(0);
    }

    public List<OrderPriceDtoInterface> getOrdersTotalPriceMonthly() {
        return orderRepository.getOrdersTotalPriceMonthly();
    }
}
