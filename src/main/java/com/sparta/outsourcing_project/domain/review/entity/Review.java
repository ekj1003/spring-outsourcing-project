package com.sparta.outsourcing_project.domain.review.entity;

import com.sparta.outsourcing_project.domain.common.Timestamped;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(length = 500)
    private String content;

    private Integer Star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;



}
