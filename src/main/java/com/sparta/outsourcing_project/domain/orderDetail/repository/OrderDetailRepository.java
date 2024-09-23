package com.sparta.outsourcing_project.domain.orderDetail.repository;

import com.sparta.outsourcing_project.domain.orderDetail.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
