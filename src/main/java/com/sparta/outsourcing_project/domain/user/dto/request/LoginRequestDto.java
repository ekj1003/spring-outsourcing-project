package com.sparta.outsourcing_project.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    @NotBlank @Email(message = "이메일 형식이 유효하지 않습니다.")
    private String email;

    @NotBlank
    private String password;
}
