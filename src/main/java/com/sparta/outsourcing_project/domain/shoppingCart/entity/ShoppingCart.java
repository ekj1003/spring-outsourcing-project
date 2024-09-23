package com.sparta.outsourcing_project.domain.shoppingCart.entity;

import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Integer quantity;

    // 총 가격이 아닌 물건 한개의 가격입니다.
    private Integer price;

    public ShoppingCart(User user, Store store, Menu menu, int quantity, int price) {
        this.user = user;
        this.store = store;
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
    }

    public ShoppingCart patch(int quantity) {
        this.quantity = quantity;
        return this;
    }
}
