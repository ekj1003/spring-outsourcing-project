package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuOwnerService {

    private final MenuRepository menuRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public MenuResponse createMenu(Long storeId, MenuRequest request, Long userId) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CannotFindStoreException());

        Menu menu = new Menu(store, request.getName(), request.getPrice(), request.getDescription());
        menuRepository.save(menu);

        return new MenuResponse(menu);
    }

    @Transactional
    public MenuResponse patchMenu(Long storeId, Long menuId, MenuPatchRequest request, Long userId) {

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
}
