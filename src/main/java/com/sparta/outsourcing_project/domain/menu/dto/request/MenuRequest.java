package com.sparta.outsourcing_project.domain.menu.dto.request;

import lombok.Getter;

@Getter
public class MenuRequest {
    private String name;
    private Integer price;
    private String description;
}