package com.sparta.outsourcing_project.domain.store.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StoreDailyDashboardRequestDto {
    private Long storeId;
    private LocalDate day;
}
