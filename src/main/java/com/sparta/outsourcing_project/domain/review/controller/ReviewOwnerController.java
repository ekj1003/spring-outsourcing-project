package com.sparta.outsourcing_project.domain.review.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.review.dto.ReviewOwnerCommentRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewOwnerCommentResponseDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.service.ReviewOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners/review")
@RequiredArgsConstructor
public class ReviewOwnerController {

    private final ReviewOwnerService reviewOwnerService;

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@Auth AuthUser authUser, @PathVariable("storeId") Long storeId) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewOwnerService.getReviews(authUser, storeId);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    // 리뷰에 대한 사장님 댓글 생성
    @PostMapping("/{reviewId}/comments")
    public ResponseEntity<ReviewOwnerCommentResponseDto> saveOwnerComment(
            @Auth AuthUser authUser,
            @PathVariable(name = "reviewId") Long reviewId,
            @Valid @RequestBody ReviewOwnerCommentRequestDto reviewOwnerCommentRequestDto
    ) {
        return ResponseEntity.ok(reviewOwnerService.saveOwnerComment(authUser, reviewId, reviewOwnerCommentRequestDto));
    }
}
