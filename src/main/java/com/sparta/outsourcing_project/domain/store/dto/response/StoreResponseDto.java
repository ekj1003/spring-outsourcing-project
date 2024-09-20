package com.sparta.outsourcing_project.domain.store.dto.response;

import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.dto.response.UserResponseDto;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class StoreResponseDto {
    private final Long id;
    private final String name;
    private final LocalTime openAt;
    private final LocalTime closeAt;
    private final Integer minPrice;
    private final Boolean isDeleted;
    private final UserResponseDto user;
    // private List<MenuResponseDto> menus; // 메뉴 목록 추가


    public StoreResponseDto(Long id, String name, LocalTime openAt, LocalTime closeAt, Integer minPrice, Boolean isDeleted, UserResponseDto user) {
        this.id = id;
        this.name = name;
        this.openAt = openAt;
        this.closeAt = closeAt;
        this.minPrice = minPrice;
        this.isDeleted = isDeleted;
        this.user = user;
    }

    public StoreResponseDto(Store store) {
        this.id = store.getId();
        this.name = store.getName();
        this.openAt = store.getOpenAt();
        this.closeAt = store.getCloseAt();
        this.minPrice = store.getMinPrice();
        this.isDeleted = store.getIsDeleted();
        this.user = new UserResponseDto(store.getUser().getId(), store.getUser().getEmail());
    }
}
