package com.sparta.outsourcing_project.domain.store.dto.response;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuListResponseDto;

import java.time.LocalTime;
import java.util.List;

public class OneStoreResponseDto {
    private Long id;
    private String name;
    private LocalTime openAt;
    private LocalTime closeAt;
    private Integer minPrice;
    private Boolean isDeleted;
    private List<MenuListResponseDto> menuList;

    public OneStoreResponseDto(Long id, String name, LocalTime openAt, LocalTime closeAt, Integer minPrice, Boolean isDeleted, List<MenuListResponseDto> menuList) {
        this.id = id;
        this.name = name;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.minPrice = minPrice;
        this.isDeleted = isDeleted;
        this.menuList = menuList;
    }
}
