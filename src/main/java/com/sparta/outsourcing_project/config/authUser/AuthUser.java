package com.sparta.outsourcing_project.config.authUser;

import com.sparta.outsourcing_project.domain.user.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUser {
    private final Long id;
    private final String email;
    private final UserType userType;
}
