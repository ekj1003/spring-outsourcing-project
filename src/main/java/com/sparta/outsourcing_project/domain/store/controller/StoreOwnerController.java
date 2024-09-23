package com.sparta.outsourcing_project.domain.store.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreDailyDashboardRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreNoticeRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StorePatchRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreDashboardResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.request.StoreMonthlyDashboardRequestDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreNoticeResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.service.StoreOwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners/stores")
@RequiredArgsConstructor
public class StoreOwnerController {

    private final StoreOwnerService storeOwnerService;

    // 가게 생성
    @PostMapping
    public ResponseEntity<StoreResponseDto> saveStore(
            @Auth AuthUser authUser,
            @Valid @RequestBody StoreRequestDto storeRequestDto
    ) {
        return ResponseEntity.ok(storeOwnerService.saveStore(authUser, storeRequestDto));
    }

    // 가게 수정
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponseDto> patchStore (
            @Auth AuthUser authUser,
            @PathVariable("storeId") Long storeId,
            @Valid @RequestBody StorePatchRequestDto storePatchRequestDto
    ) {
        StoreResponseDto storeResponseDto = storeOwnerService.patchStore(storeId, authUser, storePatchRequestDto);
        if (storePatchRequestDto.getIsDeleted()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // store 소프트 삭제시 204 No Content 반환
        }
        return ResponseEntity.ok(storeResponseDto);
    }

    // 가게 공지 생성
    @PostMapping("/notice/{storeId}")
    public ResponseEntity<StoreNoticeResponseDto> saveNotice (
            @Auth AuthUser authUser,
            @PathVariable("storeId") Long storeId,
            @Valid @RequestBody StoreNoticeRequestDto storeNoticeRequestDto
    ) {
        return ResponseEntity.ok(storeOwnerService.saveNotice(authUser, storeId, storeNoticeRequestDto));
    }

    // 가게 공지 삭제
    @DeleteMapping("/{storeId}/notice/{noticeId}")
    public ResponseEntity<Long> deleteNotice(
            @Auth AuthUser authUser,
            @PathVariable("storeId") Long storeId,
            @PathVariable("noticeId") Long noticeId
    ) {
        return ResponseEntity.ok(storeOwnerService.deleteNotice(authUser, storeId, noticeId));
    }

    // 가게 일별 대시보드 조회
    @GetMapping("/dashboard/daily")
    public ResponseEntity<StoreDashboardResponseDto> getDailyDashboard (
            @Auth AuthUser authUser,
            @Valid @RequestBody StoreDailyDashboardRequestDto storeDayDashboardRequestDto
    ) {
        return ResponseEntity.ok(storeOwnerService.getDailyDashboard(authUser, storeDayDashboardRequestDto));

    }

    // 가게 월별 대시보드 조회
    @GetMapping("/dashboard/monthly")
    public ResponseEntity<StoreDashboardResponseDto> getMonthlyDashboard (
            @Auth AuthUser authUser,
            @Valid @RequestBody StoreMonthlyDashboardRequestDto storeMonthlyDashboardRequestDto
    ) {
        return ResponseEntity.ok(storeOwnerService.getMonthlyDashboard(authUser, storeMonthlyDashboardRequestDto));
    }
}

