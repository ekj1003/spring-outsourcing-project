package com.sparta.outsourcing_project.domain.menu.dto.response;

import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.option.dto.response.OptionResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
    private List<OptionResponse> options; // 추가된 부분

    public MenuResponse(Menu menu) {
        this.menuId = menu.getId();
        this.menuType = menu.getMenuType().toString();
        this.storeId = menu.getStore().getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.description = menu.getDescription();
        this.isDeleted = menu.getIsDeleted();
        this.options = menu.getOptions().stream()
                .filter(option -> !option.getIsDeleted())
                .map(OptionResponse::new)
                .collect(Collectors.toList());
    }
}