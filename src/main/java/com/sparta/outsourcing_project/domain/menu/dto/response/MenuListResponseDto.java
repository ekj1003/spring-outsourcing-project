package com.sparta.outsourcing_project.domain.menu.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuListResponseDto {
    private Long menuId;
    private String menuType;
    private String name;
    private Integer price;
    private String description;

    public MenuListResponseDto(Long menuId, String menuType, String name, Integer price, String description) {
        this.menuId = menuId;
        this.menuType = menuType;
        this.name = name;
        this.price = price;
        this.description = description;
    }
}
