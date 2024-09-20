package com.sparta.outsourcing_project.domain.store.dto.response;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuListResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class OneStoreResponseDto {
    private final StoreResponseDto store;
    private final List<MenuListResponseDto> menuList;

    public OneStoreResponseDto(StoreResponseDto store, List<MenuListResponseDto> menuList) {
        this.store = store;
        this.menuList = menuList;
    }
}
