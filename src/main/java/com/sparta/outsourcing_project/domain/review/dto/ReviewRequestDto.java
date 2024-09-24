package com.sparta.outsourcing_project.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private String content;
    private Integer star;

    public ReviewRequestDto(String content, Integer star) {
        this.content = content;
        this.star = star;
    }
}
