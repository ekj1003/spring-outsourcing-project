package com.sparta.outsourcing_project.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderPatchRequestDto {
    private Boolean is_deleted = false;

    public OrderPatchRequestDto(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }
}
