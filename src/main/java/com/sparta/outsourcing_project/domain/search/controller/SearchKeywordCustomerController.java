package com.sparta.outsourcing_project.domain.search.controller;

import com.sparta.outsourcing_project.domain.search.dto.response.SearchKeywordResponse;
import com.sparta.outsourcing_project.domain.search.service.SearchKeywordCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers/search")
@RequiredArgsConstructor
public class SearchKeywordCustomerController {
    private final SearchKeywordCustomerService searchKeywordCustomerService;

    // 통합 검색 가게 및 메뉴 검색
    @GetMapping
    public ResponseEntity<List<SearchKeywordResponse>> searchStoresAndMenus(@RequestParam("keyword") String keyword) {
        return ResponseEntity.ok(searchKeywordCustomerService.searchStoresAndMenus(keyword));
    }

    // 인기 검색어
    @GetMapping("/top-keywords")
    public ResponseEntity<List<Map<String, Object>>> getTopSearchKeywords() {
        return ResponseEntity.ok(searchKeywordCustomerService.getTopSearchKeywords());
    }

    // 특정 메뉴 타입으로 가게와 메뉴 검색
    @GetMapping("/types")
    public ResponseEntity<List<SearchKeywordResponse>> searchStoresAndMenusByType(@RequestParam("menuType") String menuType) {
        return ResponseEntity.ok(searchKeywordCustomerService.searchStoresAndMenusByType(menuType));
    }
}
