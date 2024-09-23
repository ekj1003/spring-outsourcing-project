package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.option.dto.request.OptionRequest;
import com.sparta.outsourcing_project.domain.menu.option.entity.Option;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.exception.*;
import com.sparta.outsourcing_project.domain.user.entity.User;
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

    @Transactional
    public MenuResponse createMenu(Long storeId, MenuRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreException::new);

        if (!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("해당 가게의 메뉴를 생성할 권한이 없습니다.");
        }

        Menu menu = new Menu(store, MenuType.of(request.getMenuType()), request.getName(), request.getPrice(), request.getDescription());

        // 옵션 추가
        for (OptionRequest optionRequest : request.getOptions()) {
            Option option = new Option(menu, optionRequest.getGroupName(), optionRequest.getDetailedName(), optionRequest.getPrice());
            menu.addOption(option);
        }

        menuRepository.save(menu);
        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse patchMenu(Long storeId, Long menuId, MenuPatchRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));

        Store store = storeRepository.findById(storeId)
                .orElseThrow(CannotFindStoreException::new);

        if (!store.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedAccessException("해당 가게의 메뉴를 수정할 권한이 없습니다.");
        }

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(CannotFindMenuException::new);

        if (request.getIsDeleted()) {
            menu.setIsDeleted();
            return null;
        }

        menu.updateMenu(MenuType.of(request.getMenuType()), request.getName(), request.getPrice(), request.getDescription());

        // 기존 옵션들 삭제 처리
        for (Option option : menu.getOptions()) {
            menu.removeOption(option);
        }

        // 새 옵션 추가
        for (OptionRequest optionRequest : request.getOptions()) {
            Option option = new Option(menu, optionRequest.getGroupName(), optionRequest.getDetailedName(), optionRequest.getPrice());
            menu.addOption(option);
        }

        menuRepository.save(menu);
        return new MenuResponse(menu);
    }
}
