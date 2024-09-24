package com.sparta.outsourcing_project.domain.search.service;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.search.dto.response.SearchKeywordResponse;
import com.sparta.outsourcing_project.domain.search.entity.SearchKeyword;
import com.sparta.outsourcing_project.domain.search.repository.SearchKeywordRepository;
import com.sparta.outsourcing_project.domain.store.dto.response.StoreResponseDto;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SearchKeywordCustomerService {

    private final StoreRepository storeRepository;
    private final SearchKeywordRepository searchKeywordRepository;

    @Transactional
    public List<SearchKeywordResponse> searchStoresAndMenus(String keyword) {
        // 키워드로 가게와 메뉴 검색
        List<Store> stores = storeRepository.findByStoreOrMenuNameContaining(keyword);

        // 검색어 기록
        saveSearchKeyword(keyword);

        // Store에서 필요한 정보(가게, 사용자, 메뉴)로 변환 후 반환
        return stores.stream()
                .map(store -> {
                    StoreResponseDto storeResponseDto = new StoreResponseDto(store); // 가게 정보

                    // 가게 이름이 검색된 경우: 해당 가게의 모든 메뉴를 반환
                    List<MenuResponse> menuResponses;
                    if (store.getName().contains(keyword)) {
                        // 가게 이름이 포함된 경우: 전체 메뉴 반환
                        menuResponses = store.getMenus().stream()
                                .filter(menu -> !menu.getIsDeleted()) // 삭제되지 않은 모든 메뉴 반환
                                .map(MenuResponse::new)
                                .collect(Collectors.toList());
                    } else {
                        // 메뉴 이름이 포함된 경우: 키워드와 일치하는 메뉴만 반환
                        menuResponses = store.getMenus().stream()
                                .filter(menu -> !menu.getIsDeleted() && menu.getName().contains(keyword)) // 키워드와 일치하는 메뉴만 반환
                                .map(MenuResponse::new)
                                .collect(Collectors.toList());
                    }

                    return new SearchKeywordResponse(storeResponseDto, menuResponses);
                })
                .collect(Collectors.toList());
    }

    // 검색어 저장 및 갱신 처리
    @Transactional
    public void saveSearchKeyword(String keyword) {
        SearchKeyword searchKeyword = searchKeywordRepository.findByKeyword(keyword)
                .orElse(new SearchKeyword(keyword));

        // 만약 1시간 이상 업데이트되지 않았다면 카운트 초기화
        if (searchKeyword.isExpired()) {
            searchKeyword.resetCount();
        }

        // 카운트 증가
        searchKeyword.incrementCount();
        searchKeywordRepository.save(searchKeyword);
    }

    public List<Map<String, Object>> getTopSearchKeywords() {
        List<SearchKeyword> top10Keywords = searchKeywordRepository.findTop10ByOrderByCountDesc();

        // 검색어와 순위를 반환
        return IntStream.range(0, top10Keywords.size())
                .mapToObj(i -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("rank", i + 1); // 순위는 1부터 시작
                    result.put("keyword", top10Keywords.get(i).getKeyword());
                    return result;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SearchKeywordResponse> searchStoresAndMenusByType(String menuType) {
        MenuType type = MenuType.of(menuType); // 문자열을 Enum으로 변환
        List<Store> stores = storeRepository.findByMenuType(type);

        // 각 가게별로 가게 정보와 해당 타입의 메뉴 정보 반환
        return stores.stream()
                .map(store -> {
                    StoreResponseDto storeResponseDto = new StoreResponseDto(store); // 가게 정보

                    // 해당 타입의 메뉴들만 필터링
                    List<MenuResponse> menuResponses = store.getMenus().stream()
                            .filter(menu -> !menu.getIsDeleted() && menu.getMenuType().equals(type)) // 해당 타입 메뉴만
                            .map(MenuResponse::new)
                            .collect(Collectors.toList());

                    return new SearchKeywordResponse(storeResponseDto, menuResponses); // 가게와 메뉴 정보 반환
                })
                .collect(Collectors.toList());
    }
}
