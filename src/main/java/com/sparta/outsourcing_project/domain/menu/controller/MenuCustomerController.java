package com.sparta.outsourcing_project.domain.menu.controller;

import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.service.MenuCustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuCustomerController {
    private final MenuCustomerService menuCustomerService;

    // Retrieve Store with Menus
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getStoreWithMenus(@PathVariable Long storeId) {
        List<MenuResponse> menus = menuCustomerService.getStoreWithMenus(storeId);
        return ResponseEntity.ok(menus);
    }
}
