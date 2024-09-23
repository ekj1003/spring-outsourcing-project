package com.sparta.outsourcing_project.domain.order.repository;

import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndUserId(Long orderId, Long id);

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByStoreId(Long id);

    Order findByIdAndStoreId(Long orderId, Long storeId);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d'), COUNT(*) " +
            "FROM orders o " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m-%d')", nativeQuery = true)
    List<OrdersCountDto> countOrdersByDate();

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m'), COUNT(*) " +
            "FROM orders o " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m')", nativeQuery = true)
    List<OrdersCountDto> countOrdersByMonth();

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d'), SUM(o.price * o.quantity) " +
            "FROM orders o " +
            "WHERE o.created_at BETWEEN :start AND :end " +
            "GROUP BY 1", nativeQuery = true)
    List<OrdersPriceDto> getOrdersTotalPriceByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d'), SUM(o.price * o.quantity) " +
            "FROM orders o " +
            "GROUP BY 1", nativeQuery = true)
    List<OrdersPriceDto> getOrdersTotalPriceDaily();

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m'), SUM(o.price * o.quantity) " +
            "FROM orders o " +
            "GROUP BY 1", nativeQuery = true)
    List<OrdersPriceDto> getOrdersTotalPriceMonthly();

    List<Order> findAllByStoreIdAndCreatedAtBetween(Long storeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
