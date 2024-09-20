package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
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

    public List<MenuResponse> getStoreWithMenus(Long storeId) {
        List<Menu> menus = menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId);
        return menus.stream()
                .map(MenuResponse::new)
                .collect(Collectors.toList());
    }
}
