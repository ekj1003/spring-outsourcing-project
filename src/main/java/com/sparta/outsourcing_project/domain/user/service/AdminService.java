package com.sparta.outsourcing_project.domain.user.service;

import com.sparta.outsourcing_project.domain.order.repository.OrderRepository;
import com.sparta.outsourcing_project.domain.user.dto.response.AdminUsersDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        return new OrdersCountDto(date, count);
    }

    public List<OrdersCountDto> getOrdersCountedDaily() {
        return orderRepository.countOrdersByDate();
    }

    public OrdersCountDto getOrdersCountByMonth(LocalDate date) {
        LocalDateTime startOfDate = date.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfDate = date.withDayOfMonth(date.lengthOfMonth()).atTime(23, 59, 59);
        Long count = orderRepository.countByCreatedAtBetween(startOfDate, endOfDate);
        return new OrdersCountDto(date, count);
    }

    public List<OrdersCountDto> getOrdersCountedMonthly() {
        return orderRepository.countOrdersByMonth();
    }

    public OrdersPriceDto getOrdersTotalPriceByDate(LocalDate date) {
        LocalDateTime startOfDate = date.atStartOfDay();
        LocalDateTime endOfDate = startOfDate.plusDays(1);
        return orderRepository.getOrdersTotalPriceByDateRange(startOfDate, endOfDate).get(0);
    }

    public List<OrdersPriceDto> getOrdersTotalPriceDaily() {
        return orderRepository.getOrdersTotalPriceDaily();
    }

    public OrdersPriceDto getOrdersTotalPriceByMonth(LocalDate date) {
        LocalDateTime startOfDate = date.withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfDate = date.withDayOfMonth(date.lengthOfMonth()).atTime(23, 59, 59);
        return orderRepository.getOrdersTotalPriceByDateRange(startOfDate, endOfDate).get(0);
    }

    public List<OrdersPriceDto> getOrdersTotalPriceMonthly() {
        return orderRepository.getOrdersTotalPriceMonthly();
    }
}
