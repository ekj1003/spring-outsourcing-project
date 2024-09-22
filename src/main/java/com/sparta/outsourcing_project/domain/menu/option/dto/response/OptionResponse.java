package com.sparta.outsourcing_project.domain.menu.option.dto.response;

import com.sparta.outsourcing_project.domain.menu.option.entity.Option;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionResponse {
    private String groupName;
    private String detailedName;
    private Integer price;

    public OptionResponse(Option option) {
        this.groupName = option.getGroupName();
        this.detailedName = option.getDetailedName();
        this.price = option.getPrice();
    }
}
