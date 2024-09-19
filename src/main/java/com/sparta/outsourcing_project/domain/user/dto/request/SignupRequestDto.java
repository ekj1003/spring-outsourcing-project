package com.sparta.outsourcing_project.domain.user.dto.request;

import com.sparta.outsourcing_project.domain.user.enums.UserType;
import lombok.Data;

@Data
public class SignupRequestDto {
    private String email;
    private String password;
}
