package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    private void validateOwner(Long storeId, Long userId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CannotFindStoreException());

        User storeOwner = store.getUser();
        if (storeOwner == null || !storeOwner.getId().equals(userId) || storeOwner.getAuth() != UserType.OWNER) {
            throw new OwnerNotAuthorizedException();
        }
    }

    @Transactional
    public MenuResponse createMenu(Long storeId, MenuRequest request, Long userId) {
        validateOwner(storeId, userId);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CannotFindStoreException());

        Menu menu = new Menu(store, request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse patchMenu(Long storeId, Long menuId, MenuPatchRequest request, Long userId) {
        validateOwner(storeId, userId);

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CannotFindMenuException());

        if (request.getIsDeleted()) {
            menu.setIsDeleted();
            menuRepository.save(menu);
            return null;
        }

        menu.updateMenu(request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    public List<MenuResponse> getStoreWithMenus(Long storeId) {
        List<Menu> menus = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId);
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }
}
