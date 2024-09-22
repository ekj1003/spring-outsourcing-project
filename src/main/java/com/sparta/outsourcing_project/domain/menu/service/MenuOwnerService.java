package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.exception.*;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuOwnerService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    // 메뉴 생성 메서드
    @Transactional
    public MenuResponse createMenu(Long storeId, MenuRequest request, Long userId) {
        // 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        // 가게 존재 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreException::new);

        // 사용자 권한 확인
        if (!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("해당 가게의 메뉴를 생성할 권한이 없습니다.");
        }

        // 메뉴 생성 및 저장
        Menu menu = new Menu(store, MenuType.of(request.getMenuType()), request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    // 메뉴 수정 및 삭제 메서드
    @Transactional
    public MenuResponse patchMenu(Long storeId, Long menuId, MenuPatchRequest request, Long userId) {
        // 사용자 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        // 가게 존재 여부 확인
        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreException::new);

        // 사용자 권한 확인
        if (!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("해당 가게의 메뉴를 수정할 권한이 없습니다.");
        }

        // 메뉴 존재 여부 확인
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(CannotFindMenuException::new);

        // 삭제 요청일 경우
        if (request.getIsDeleted()) {
            menu.setIsDeleted();
            menuRepository.save(menu);
            return null;
        }

        // 메뉴 수정
        menu.updateMenu(MenuType.of(request.getMenuType()), request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }
}
