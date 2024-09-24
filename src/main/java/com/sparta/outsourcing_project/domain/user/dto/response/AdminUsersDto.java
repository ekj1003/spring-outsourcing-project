package com.sparta.outsourcing_project.domain.user.dto.response;

import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AdminUsersDto {
    private Long id;
    private String email;
    private UserType userType;
    private Boolean isDeleted;

    public AdminUsersDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.userType = user.getUserType();
        this.isDeleted = user.getIsDeleted();
    }
}
