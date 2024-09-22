package com.sparta.outsourcing_project.domain.review.dto.response;

import com.sparta.outsourcing_project.domain.review.entity.ReviewOwnerComment;
import lombok.Getter;

@Getter
public class ReviewOwnerCommentResponseDto {
    private Long reviewId;
    private Long ownerCommentId;
    private String ownerComment;

    public ReviewOwnerCommentResponseDto(ReviewOwnerComment reviewOwnerComment) {
        this.reviewId = reviewOwnerComment.getReview().getId();
        this.ownerCommentId = reviewOwnerComment.getId();
        this.ownerComment = reviewOwnerComment.getOwnerComment();
    }
}
