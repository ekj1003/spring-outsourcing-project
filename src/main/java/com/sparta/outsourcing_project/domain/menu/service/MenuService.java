package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    // Validate if the authenticated user is the owner of the store
    private void validateOwner(Long storeId, Long userId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new InvalidRequestException("Store not found"));

        if (!store.getOwnerId().equals(userId)) {
            throw new InvalidRequestException("User is not the owner of the store");
        }
    }

    @Transactional
    public MenuResponse createMenu(Long storeId, MenuRequest request)//, Long userId)
    {
        validateOwner(storeId, userId);

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new InvalidRequestException("Store not found"));

        Menu menu = new Menu(store, request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse updateMenu(Long storeId, Long menuId, MenuRequest request, Long userId) {
        validateOwner(storeId, userId);

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new InvalidRequestException("Menu not found"));

        menu.updateMenu(request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    @Transactional
    public void deleteMenu(Long storeId, Long menuId, Boolean isDeleted, Long userId) {
        // Perform validation and soft delete logic
        validateOwner(storeId, userId);
        Menu menu = findMenuById(menuId);
        menu.setIsDeleted(isDeleted);
        menuRepository.save(menu);
    }


    @Transactional(readOnly = true)
    public List<com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse> getStoreWithMenus(Long storeId) {
        List<Menu> menus = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId);
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }
}
