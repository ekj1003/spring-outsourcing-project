package com.sparta.outsourcing_project.domain.review.entity;

import com.sparta.outsourcing_project.domain.common.Timestamped;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
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

    private Integer star;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Review(User newUser, Order findOrder, ReviewRequestDto reviewRequestDto) {
        content = reviewRequestDto.getContent();
        star = reviewRequestDto.getStar();
        order = findOrder;
        user = newUser;
    }

    public Review patchReview(ReviewRequestDto reviewRequestDto) {
        content = reviewRequestDto.getContent();
        star = reviewRequestDto.getStar();
        return this;
    }
}
