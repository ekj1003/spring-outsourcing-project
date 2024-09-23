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

    @Query(value = "SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto(FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m-%d'), COUNT(o)) " +
            "FROM Order o ")
    List<OrdersCountDto> countOrdersByDate();

//    @Query("SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto(FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m'), COUNT(o)) " +
//            "FROM Order o " +
//            "GROUP BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m')")
//    List<OrdersCountDto> countOrdersByMonth();
//
//    @Query("SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto(FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m-%d'), SUM(o.price * o.quantity)) " +
//            "FROM Order o " +
//            "WHERE o.createdAt BETWEEN :start AND :end " +
//            "GROUP BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m-%d')")
//    List<OrdersPriceDto> getOrdersTotalPriceByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
//
//    @Query("SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto(FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m'), SUM(o.price * o.quantity)) " +
//            "FROM Order o " +
//            "GROUP BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m-%d')")
//    List<OrdersPriceDto> getOrdersTotalPriceDaily();
//
//    @Query("SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto(FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m'), SUM(o.price * o.quantity)) " +
//            "FROM Order o " +
//            "GROUP BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m')")
//    List<OrdersPriceDto> getOrdersTotalPriceMonthly();
}
