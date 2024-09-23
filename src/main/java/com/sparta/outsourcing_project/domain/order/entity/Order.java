package com.sparta.outsourcing_project.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.common.Timestamped;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.orderDetail.entity.OrderDetail;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer totalPrice;

    private Integer totalPrice;

    @Column(nullable = false)
    private Boolean isDeleted = false; // isDeleted 값을 false로 초기화

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    List<OrderDetail> orderDetailList = new ArrayList<>();

    public Order(User user, Store store, int totalPrice) {
        this.user = user;
        this.store = store;
        this.totalPrice = totalPrice;
    }

    public void updateOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Order updateStatus(Status status) {
        this.status = status;
        return this;
    }

    public Long delete() {
        this.isDeleted = true;
        return id;
    }
}
