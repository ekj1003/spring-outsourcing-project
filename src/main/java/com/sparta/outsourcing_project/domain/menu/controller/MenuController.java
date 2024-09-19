package com.sparta.outsourcing_project.domain.menu.controller;

import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuDeleteRequest;
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
            @RequestBody MenuRequest request)//,
            //@Auth AuthUser authUser)
            {
        MenuResponse response = menuService.createMenu(storeId, request);//, authUser.getUserId());
        return ResponseEntity.ok(response);
    }

    // Update Menu
    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuResponse> updateMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuRequest request)//,
            //@Auth AuthUser authUser)
            {
        MenuResponse response = menuService.updateMenu(storeId, menuId, request);//, authUser.getUserId());
        return ResponseEntity.ok(response);
    }

    // Soft Delete Menu
    @PatchMapping("/{menuId}/delete")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable Long storeId,
            @PathVariable Long menuId,
            @RequestBody MenuDeleteRequest request)//,
            //@Auth AuthUser authUser)
            {
        menuService.deleteMenu(storeId, menuId, request.getIsDeleted());//, authUser.getUserId());
        return ResponseEntity.ok().build();
    }


    // Retrieve Store with Menus
    @GetMapping
    public ResponseEntity<List<MenuResponse>> getStoreWithMenus(@PathVariable Long storeId) {
        List<MenuResponse> menus = menuService.getStoreWithMenus(storeId);
        return ResponseEntity.ok(menus);
    }
}
