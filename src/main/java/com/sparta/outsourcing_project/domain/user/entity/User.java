package com.sparta.outsourcing_project.domain.user.entity;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public User(String email, String password, UserType userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    private User(Long id, String email, UserType userType) {
        this.id = id;
        this.email = email;
        this.userType = userType;
    }

    public void changePassword(String password) {
        this.password = password;
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
