package com.sparta.outsourcing_project.domain.store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.store.dto.request.StorePatchRequestDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.List;


@Entity
@Getter
@Table(name = "store")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=100, nullable = false)
    private String name;

    private LocalTime openAt;

    private LocalTime closeAt;

    @Column(nullable = false)
    private Integer minPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Boolean isDeleted = false; //isDeleted 값을 false로 초기화

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY)
    private List<Menu> menus;


    public Store(String name, LocalTime openAt, LocalTime closeAt, Integer minPrice, User user) {
        this.name = name;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.minPrice = minPrice;
        this.user = user;
    }

    public Store patchStore(StorePatchRequestDto storePatchRequestDto) {
        if (storePatchRequestDto.getName() != null) {
            this.name = storePatchRequestDto.getName();
        }
        if (storePatchRequestDto.getOpenAt() != null) {
            this.openAt = storePatchRequestDto.getOpenAt();
        }
        if (storePatchRequestDto.getCloseAt() != null) {
            this.closeAt = storePatchRequestDto.getCloseAt();
        }
        if (storePatchRequestDto.getMinPrice() != null) {
            this.minPrice = storePatchRequestDto.getMinPrice();
        }
        return this;
    }

    public Long delete() {
        this.isDeleted = true;
        return id;
    }
}

