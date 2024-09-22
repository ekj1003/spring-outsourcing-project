package com.sparta.outsourcing_project.domain.menu.dto.response;

import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponse {
    private Long menuId;
    private String menuType;
    private Long storeId;
    private String name;
    private Integer price;
    private String description;
    private Boolean isDeleted;

    public MenuResponse(Menu menu) {
        this.menuId = menu.getId();
        this.menuType = menu.getMenuType().toString();
        this.storeId = menu.getStore().getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.isDeleted = menu.getIsDeleted();
    }
}