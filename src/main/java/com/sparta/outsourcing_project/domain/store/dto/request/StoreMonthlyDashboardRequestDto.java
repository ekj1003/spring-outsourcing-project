package com.sparta.outsourcing_project.domain.store.dto.request;

import lombok.Getter;

@Getter
public class StoreMonthlyDashboardRequestDto {
    private Long storeId;
    private int year;
    private int month;
}
