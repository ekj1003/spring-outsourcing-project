package com.sparta.outsourcing_project.domain.store.entity;

import com.sparta.outsourcing_project.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;


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

    @Column(nullable = false)
    private Integer minPrice;

    @Column(nullable = false)
    @ColumnDefault("false") // isDeleted 값을 false로 초기화
    private Boolean isDeleted;

    private LocalTime openAt;
    private LocalTime closeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
