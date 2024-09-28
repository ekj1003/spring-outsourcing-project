package com.sparta.outsourcing_project.domain.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.outsourcing_project.domain.order.entity.QOrder.order;

public class OrderQueryDslRepositoryImpl implements OrderQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public OrderQueryDslRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrdersCountDto> countOrdersByDate() {
        return queryFactory
                .select(Projections.constructor(OrdersCountDto.class,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt).as("orderDate"), order.count().as("totalOrderCount")))
                .from(order)
                .groupBy(Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt))
                .fetch();
    }

    @Override
    public List<OrdersCountDto> countOrdersByMonth() {
        return queryFactory
                .select(Projections.constructor(OrdersCountDto.class,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m')", order.createdAt).as("orderDate"), order.count().as("totalOrderCount")))
                .from(order)
                .groupBy(Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m')", order.createdAt))
                .fetch();
    }

    @Override
    public List<OrdersPriceDto> getOrdersTotalPriceByDateRange(LocalDateTime start, LocalDateTime end) {
        return queryFactory
                .select(Projections.constructor(OrdersPriceDto.class,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt).as("orderDate"), order.totalPrice.sum().as("totalPrice")))
                .from(order)
                .where(order.createdAt.between(start, end))
                .groupBy(Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt))
                .fetch();
    }

    @Override
    public List<OrdersPriceDto> getOrdersTotalPriceDaily() {
        return queryFactory
                .select(Projections.constructor(OrdersPriceDto.class,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt).as("orderDate"), order.totalPrice.sum().as("totalPrice")))
                .from(order)
                .groupBy(Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m-%d')", order.createdAt))
                .fetch();
    }

    @Override
    public List<OrdersPriceDto> getOrdersTotalPriceMonthly() {
        return queryFactory
                .select(Projections.constructor(OrdersPriceDto.class,
                        Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m')", order.createdAt).as("orderDate"), order.totalPrice.sum().as("totalPrice")))
                .from(order)
                .groupBy(Expressions.stringTemplate("DATE_FORMAT({0}, '%Y-%m')", order.createdAt))
                .fetch();
    }
}
