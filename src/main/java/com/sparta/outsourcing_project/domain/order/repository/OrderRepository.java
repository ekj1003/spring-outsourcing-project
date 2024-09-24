package com.sparta.outsourcing_project.domain.order.repository;

import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.user.dto.response.OrderCountDtoInterface;
import com.sparta.outsourcing_project.domain.user.dto.response.OrderPriceDtoInterface;
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

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d') as orderDate, COUNT(*) as totalOrderCount " +
            "FROM orders o " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m-%d')", nativeQuery = true)
    List<OrderCountDtoInterface> countOrdersByDate();

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m') as orderDate, COUNT(*) as totalOrderCount " +
            "FROM orders o " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m')", nativeQuery = true)
    List<OrderCountDtoInterface> countOrdersByMonth();

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d') as orderDate, SUM(o.total_price) as totalPrice " +
            "FROM orders o " +
            "WHERE o.created_at BETWEEN :start AND :end " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m-%d')", nativeQuery = true)
    List<OrderPriceDtoInterface> getOrdersTotalPriceByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m-%d') as orderDate, SUM(o.total_price) as totalPrice " +
            "FROM orders o " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m-%d')", nativeQuery = true)
    List<OrderPriceDtoInterface> getOrdersTotalPriceDaily();

    @Query(value = "SELECT DATE_FORMAT(o.created_at, '%Y-%m') as orderDate, SUM(o.total_price) as totalPrice " +
            "FROM orders o " +
            "GROUP BY DATE_FORMAT(o.created_at, '%Y-%m')", nativeQuery = true)
    List<OrderPriceDtoInterface> getOrdersTotalPriceMonthly();

    List<Order> findAllByStoreIdAndCreatedAtBetween(Long storeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
