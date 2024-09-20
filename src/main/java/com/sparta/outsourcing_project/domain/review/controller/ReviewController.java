package com.sparta.outsourcing_project.domain.review.controller;

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
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("orderId") Long orderId, @RequestBody ReviewRequestDto reviewRequestDto) {
        ReviewResponseDto reviewResponseDto = reviewService.createReview(orderId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<List<ReviewResponseDto>> getReviews() {  // @PathVariable("storeId") Long storeId
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.getReviews(); // storeId
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDtoList);
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> patchReview(@PathVariable("reviewId") Long reviewId, @RequestBody ReviewRequestDto reviewRequestDto){
        ReviewResponseDto reviewResponseDto = reviewService.patchReview(reviewId, reviewRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(reviewResponseDto);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") Long reviewId) {
        reviewService.deleteReview(reviewId);
    }

}
