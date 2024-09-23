package com.sparta.outsourcing_project.domain.review.dto;

import com.sparta.outsourcing_project.domain.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewResponseDto {
    private Long id;
    private Long orderId;
    private String content;
    private Integer star;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public ReviewResponseDto(Review saveReview) {
        id = saveReview.getId();
        orderId = saveReview.getOrder().getId();
        content = saveReview.getContent();
        star = saveReview.getStar();
        createAt = saveReview.getCreatedAt();
        updateAt = saveReview.getUpdatedAt();
    }
}
