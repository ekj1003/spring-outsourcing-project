package com.sparta.outsourcing_project.domain.menu.entity;

import com.sparta.outsourcing_project.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Table(name = "menus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

//    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
//    private List<Menu> menus;

    @Column(length = 255)
    private String name;

    private Integer price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public Menu(Store store, String name, Integer price, String description) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.description = description;
        this.isDeleted = false;
    }

    public void updateMenu(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void setIsDeleted() {
        this.isDeleted = true;
    }
}