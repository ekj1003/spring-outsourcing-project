package com.sparta.outsourcing_project.domain.store.dto.response;

import lombok.Getter;

@Getter
public class StoreDashboardResponseDto {
    private final Long storeId;
    private final Long userCount;
    private final Long totalSales;

    public StoreDashboardResponseDto(Long id, long userCount, long totalSales) {
        this.storeId = id;
        this.userCount = userCount;
        this.totalSales = totalSales;
    }
}
