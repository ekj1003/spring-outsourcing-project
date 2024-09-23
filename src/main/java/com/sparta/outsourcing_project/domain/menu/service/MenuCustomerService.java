package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuListResponseDto;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuCustomerService {
    private final MenuRepository menuRepository;

    // 가게 ID로 메뉴 목록을 조회하는 메서드
    public List<MenuResponse> getStoreWithMenus(Long storeId, Long userId) {
        // 가게 ID와 삭제되지 않은 메뉴들 조회
        List<Menu> menus = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId);
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }

    // 특정 가게에서 메뉴 타입별로 조회하는 메서드
    public List<MenuListResponseDto> getMenusByType(Long storeId, String menuType) {
        MenuType type = MenuType.of(menuType); // 문자열을 Enum으로 변환
        List<Menu> menus = menuRepository.findByStoreIdAndMenuTypeAndIsDeletedFalse(storeId, type);

        return menus.stream()
                .map(menu -> new MenuListResponseDto(menu.getId(), menu.getMenuType().toString(), menu.getName(), menu.getPrice(), menu.getDescription()))
                .collect(Collectors.toList());
    }
}
