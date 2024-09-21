package com.sparta.outsourcing_project.domain.store.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.store.dto.response.FavoriteStoreResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.OneStoreResponseDto;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.service.StoreCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/stores")
@RequiredArgsConstructor
public class StoreCustomerController {

    private final StoreCustomerService storeCustomerService;

    // 가게 다건 조회
    @GetMapping("/many/{storeName}")
    public ResponseEntity<List<StoreResponseDto>> getStores(
            @PathVariable("storeName") String storeName
    ) {
        return ResponseEntity.ok(storeCustomerService.getStores(storeName));
    }

    // 가게 단건 조회
    @GetMapping("/one/{storeId}")
    public ResponseEntity<OneStoreResponseDto> getStore (
            @PathVariable("storeId") Long storeId
    ) {
        return ResponseEntity.ok(storeCustomerService.getStore(storeId));
    }

    // 가게 즐겨찾기
    @PostMapping("/favorites/{storeId}")
    public ResponseEntity<FavoriteStoreResponseDto> saveFavorites (
            @Auth AuthUser authUser,
            @PathVariable("storeId") Long storeId
    ) {
        return ResponseEntity.ok(storeCustomerService.saveFavorites(authUser, storeId));
    }
}
