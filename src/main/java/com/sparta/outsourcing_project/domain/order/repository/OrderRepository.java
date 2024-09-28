package com.sparta.outsourcing_project.domain.order.repository;

import com.sparta.outsourcing_project.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderQueryDslRepository {
    Order findByIdAndUserId(Long orderId, Long id);

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByStoreId(Long id);

    Order findByIdAndStoreId(Long orderId, Long storeId);

    Long countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Order> findAllByStoreIdAndCreatedAtBetween(Long storeId, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
