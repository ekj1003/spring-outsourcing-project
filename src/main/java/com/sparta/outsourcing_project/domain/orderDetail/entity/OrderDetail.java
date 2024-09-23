package com.sparta.outsourcing_project.domain.orderDetail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer price;

    private Integer quantity;

    public OrderDetail(ShoppingCart shoppingCartList, Order order) {
        this.store = shoppingCartList.getStore();
        this.menu = shoppingCartList.getMenu();
        this.price = shoppingCartList.getPrice();
        this.quantity = shoppingCartList.getQuantity();
        this.order = order;

    }
}
