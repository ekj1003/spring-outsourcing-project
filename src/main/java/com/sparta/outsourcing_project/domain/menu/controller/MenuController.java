package com.sparta.outsourcing_project.domain.menu.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // Create Menu
    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @PathVariable Long storeId,
            @RequestBody MenuRequest request,
            @Auth AuthUser authUser) {
        MenuResponse response = menuService.createMenu(storeId, request, authUser.getId());
        return ResponseEntity.ok(response);
    }

    // Update & Soft Delete Menu
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponse> patchMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuPatchRequest request,
            @Auth AuthUser authUser) {
        MenuResponse response = menuService.patchMenu(storeId, menuId, request, authUser.getId());
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    // Retrieve Store with Menus
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getStoreWithMenus(@PathVariable Long storeId) {
        List<MenuResponse> menus = menuService.getStoreWithMenus(storeId);
        return ResponseEntity.ok(menus);
    }
}
