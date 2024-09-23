package com.sparta.outsourcing_project.domain.review.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.review.dto.ReviewRequestDto;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.service.ReviewCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/review")
@RequiredArgsConstructor
public class ReviewCustomerController {

    private final ReviewCustomerService reviewCustomerService;

    @PostMapping("/{orderId}")
    public ResponseEntity<ReviewResponseDto> createReview(@Auth AuthUser authUser, @PathVariable("orderId") Long orderId, @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewCustomerService.createReview(authUser, orderId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @GetMapping()
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@Auth AuthUser authUser) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewCustomerService.getReviews(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> patchReview(@Auth AuthUser authUser,@PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewCustomerService.patchReview(authUser, reviewId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@Auth AuthUser authUser, @PathVariable("reviewId") Long reviewId) {
        reviewCustomerService.deleteReview(authUser, reviewId);
    }

}
