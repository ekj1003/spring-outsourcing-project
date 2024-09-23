package com.sparta.outsourcing_project.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteRequestDto {
    private String email;
    private String password;
}
