package com.sparta.outsourcing_project.domain.order.repository;

import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByIdAndUserId(Long orderId, Long id);

    List<Order> findAllByUserId(Long userId);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto(CAST(o.createdAt AS date), COUNT(o)) " +
            "FROM Order o " +
            "GROUP BY o.createdAt")
    List<OrdersCountDto> countOrdersByDate();

    @Query("SELECT new com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto(FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m'), COUNT(o)) " +
            "FROM Order o " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.createdAt, '%Y-%m')")
    List<OrdersCountDto> countOrdersByMonth();

}
