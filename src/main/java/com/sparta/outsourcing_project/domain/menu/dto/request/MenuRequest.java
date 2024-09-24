package com.sparta.outsourcing_project.domain.menu.dto.request;

import com.sparta.outsourcing_project.domain.menu.option.dto.request.OptionRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MenuRequest {
    private String menuType;
    private String name;
    private Integer price;
    private String description;
    private List<OptionRequest> options;
}