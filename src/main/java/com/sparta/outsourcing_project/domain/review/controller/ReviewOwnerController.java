package com.sparta.outsourcing_project.domain.review.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.review.dto.request.ReviewOwnerCommentRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.response.ReviewOwnerCommentResponseDto;
import com.sparta.outsourcing_project.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners/review")
@RequiredArgsConstructor
public class ReviewOwnerController {

    private final ReviewService reviewService;

    // 리뷰에 대한 사장님 댓글 생성
    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<ReviewOwnerCommentResponseDto> saveOwnerComment(
            @Auth AuthUser authUser,
            @PathVariable(name = "reviewId") Long reviewId,
            @Valid @RequestBody ReviewOwnerCommentRequestDto reviewOwnerCommentRequestDto
    ) {
       return ResponseEntity.ok(reviewService.saveOwnerComment(authUser, reviewId, reviewOwnerCommentRequestDto));
    }
}
