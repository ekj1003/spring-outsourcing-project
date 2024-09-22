package com.sparta.outsourcing_project.domain.user.entity;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.store.entity.FavoriteStore;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 150, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false)
    private Boolean isDeleted = false;

    private Integer store_number = 0;

    @Column(unique = true)
    private Long kakaoId;

    // 즐겨찾기 가게 목록
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Store> favoriteStores = new ArrayList<>();

    private User(Long id, String email, UserType userType) {
        this.id = id;
        this.email = email;
        this.userType = userType;
    }

    public User(String email, String password, UserType userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public User(String email, String password, UserType userType, Long kakaoId) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.kakaoId = kakaoId;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateKakaoId(Long kakaoId) {
        this.kakaoId = kakaoId;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserType());
    }

    public void deleteAccount() {
        this.isDeleted = true;
    }

    public void incrementStoreNumber() {
        this.store_number++;
    }

    public void decrementStoreNumber() {
        this.store_number--;
    }
}
