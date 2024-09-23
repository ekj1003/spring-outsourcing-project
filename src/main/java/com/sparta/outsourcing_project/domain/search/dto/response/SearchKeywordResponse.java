package com.sparta.outsourcing_project.domain.search.dto.response;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class SearchKeywordResponse {
    private final StoreResponseDto store;
    private final List<MenuResponse> menus; // 여러 개의 메뉴를 저장하는 리스트

    public SearchKeywordResponse(StoreResponseDto store, List<MenuResponse> menus) {
        this.store = store;
        this.menus = menus; // 리스트로 저장
    }
}