package com.sparta.outsourcing_project.domain.store.dto.request;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StorePatchRequestDto {
    private String name;
    private LocalTime openAt;
    private LocalTime closeAt;
    private Integer minPrice;
    private Boolean isDeleted = false;  // 삭제 플래그
}