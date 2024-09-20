package com.sparta.outsourcing_project.domain.user.entity;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

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
    private Boolean isDeleted = false; // isDeleted 값을 false로 초기화

    private Integer store_number;

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

    public void incrementStoreNumber() {
        if (this.store_number == null) {
            this.store_number = 1;
        } else {
            this.store_number++;
        }
    }

    public void decrementStoreNumber() {
        this.store_number--;
    }
}
