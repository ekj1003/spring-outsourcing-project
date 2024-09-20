package com.sparta.outsourcing_project.domain.review.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.entity.Review;
import com.sparta.outsourcing_project.domain.review.repository.ReviewRepository;
import com.sparta.outsourcing_project.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ReviewResponseDto> createReview(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId, @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(authUser, orderId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@Auth AuthUser authUser) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviews(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> patchReview(@Auth AuthUser authUser,@PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.patchReview(authUser, reviewId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@Auth AuthUser authUser, @PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(authUser, reviewId);
    }

}
