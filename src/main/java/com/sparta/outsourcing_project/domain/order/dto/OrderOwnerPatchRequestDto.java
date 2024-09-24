package com.sparta.outsourcing_project.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderOwnerPatchRequestDto {
    private String status;

    public OrderOwnerPatchRequestDto(String status) {
        this.status = status;
    }
}
