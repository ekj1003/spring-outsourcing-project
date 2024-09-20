package com.sparta.outsourcing_project.domain.order.entity;

import com.sparta.outsourcing_project.domain.order.enums.Status;
import com.sparta.outsourcing_project.domain.common.Timestamped;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Integer price;

    @Column(nullable = false)
    private Boolean isDeleted = false; // isDeleted 값을 false로 초기화

    @Enumerated(EnumType.STRING)
    private Status status;

    public Order(User user, Menu menu, Store store, int price){
        this.user = user;
        this.menu = menu;
        this.store = store;
        this.price = price;
        this.status = Status.ORDERED;
    }

    public Order patchOrder(Menu menu) {
        this.menu = menu;
        return this;
    }

    public Long delete() {
        this.isDeleted = true;
        return id;
    }
}
