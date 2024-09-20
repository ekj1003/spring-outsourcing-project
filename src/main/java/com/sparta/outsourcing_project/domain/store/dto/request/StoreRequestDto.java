package com.sparta.outsourcing_project.domain.store.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequestDto {
    private String name;
    private LocalTime openAt;
    private LocalTime closeAt;
    private Integer minPrice;

}
