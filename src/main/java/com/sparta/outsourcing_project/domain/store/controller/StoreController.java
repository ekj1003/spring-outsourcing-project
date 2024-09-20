package com.sparta.outsourcing_project.domain.store.controller;

import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    // 가게 다건 조회
    @GetMapping("/stores/{storeName}")
    public ResponseEntity<List<StoreResponseDto>> getStores(
            @PathVariable("storeName") String storeName
    ) {
        return ResponseEntity.ok(storeService.getStores(storeName));
    }

//    // 가게 단건 조회
//    @GetMapping("/stores/{storeId}")
//    public ResponseEntity<StoreResponseDto> getStore (
//            @PathVariable("storeId") Long storeId
//    ) {
//        return ResponseEntity.ok(storeService.getStore(storeId));
//    }
}
