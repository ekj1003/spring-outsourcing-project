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
    private String groupName;

    @Column(length = 255)
    private String detailedName;

    private Integer price;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    public Option(Menu menu, String groupName, String detailedName, Integer price) {
        this.menu = menu;
        this.groupName = groupName;
        this.detailedName = detailedName;
        this.price = price;
        this.isDeleted = false;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void updateOption(String groupName, String detailedName, Integer price) {
        this.groupName = groupName;
        this.detailedName = detailedName;
        this.price = price;
    }

    public void setIsDeletedOption() {
        this.isDeleted = true;
    }
}
