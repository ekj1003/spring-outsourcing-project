package com.sparta.outsourcing_project.domain.menu.dto.request;

import com.sparta.outsourcing_project.domain.menu.option.dto.request.OptionRequest;
import lombok.Getter;

import java.util.List;

@Getter
public class MenuPatchRequest {
    private String menuType;
    private String name;
    private Integer price;
    private String description;
    private Boolean isDeleted = false;
    private List<OptionRequest> options;
}
