package com.sparta.outsourcing_project.domain.menu.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.service.MenuOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuOwnerController {

    private final MenuOwnerService menuOwnerService;

    // Create Menu
    @PostMapping
    public ResponseEntity<MenuResponse> createMenu(
            @PathVariable Long storeId,
            @RequestBody MenuRequest request,
            @Auth AuthUser authUser) {
        MenuResponse response = menuOwnerService.createMenu(storeId, request, authUser.getId());
        return ResponseEntity.ok(response);
    }

    // Update & Soft Delete Menu
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponse> patchMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuPatchRequest request,
            @Auth AuthUser authUser) {
        MenuResponse response = menuOwnerService.patchMenu(storeId, menuId, request, authUser.getId());
        if (response == null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok(response);
    }
}
