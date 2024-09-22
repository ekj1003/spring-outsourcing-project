package com.sparta.outsourcing_project.domain.menu.option.entity;

import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "options")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "options_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(length = 255)
    private String name;

    private Integer price;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public Option(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isDeleted = false;
    }

    public void updateOption(String name, Integer price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public void setIsDeletedOption() {
        this.isDeleted = true;
    }
}
