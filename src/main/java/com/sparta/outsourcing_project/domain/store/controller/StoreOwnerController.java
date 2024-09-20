package com.sparta.outsourcing_project.domain.store.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.store.dto.request.StorePatchRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.service.StoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StoreOwnerController {

    private final StoreService storeService;

    // 가게 생성
    @PostMapping("/stores")
    public ResponseEntity<StoreResponseDto> saveStore(
            @Auth AuthUser authUser,
            @Valid @RequestBody StoreRequestDto storeRequestDto
    ) {
        return ResponseEntity.ok(storeService.saveStore(authUser, storeRequestDto));
    }

    // 가게 수정
    @PatchMapping("/stores/{storeId}")
    public ResponseEntity<StoreResponseDto> patchStore (
            @Auth AuthUser authUser,
            @PathVariable("storeId") Long storeId,
            @Valid @RequestBody StorePatchRequestDto storePatchRequestDto
    ) {
        StoreResponseDto storeResponseDto = storeService.patchStore(storeId, authUser, storePatchRequestDto);
        if (storePatchRequestDto.getIsDeleted()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // store 소프트 삭제시 204 No Content 반환
        }
        return ResponseEntity.ok(storeResponseDto);
    }
}

