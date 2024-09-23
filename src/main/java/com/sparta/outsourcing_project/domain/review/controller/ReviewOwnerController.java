package com.sparta.outsourcing_project.domain.review.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.review.dto.ReviewResponseDto;
import com.sparta.outsourcing_project.domain.review.service.ReviewOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
