package com.sparta.outsourcing_project.domain.shoppingCart.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartPatchRequestDto;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartRequestDto;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartResponseDto;
import com.sparta.outsourcing_project.domain.shoppingCart.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers/shopping-cart")
@RequiredArgsConstructor
public class ShoppingCartCustomerController {

    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCartResponseDto> createShoppingCart(@Auth AuthUser authUser, @RequestBody ShoppingCartRequestDto shoppingCartRequestDto) {
        ShoppingCartResponseDto shoppingCartResponseDto = shoppingCartService.createShoppingCart(authUser, shoppingCartRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCartResponseDto>> getAllShoppingCart(@Auth AuthUser authUser) {
        List<ShoppingCartResponseDto> shoppingCartResponseDtoList = shoppingCartService.getAllShoppringCart(authUser);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartResponseDtoList);
    }

    @PatchMapping("/{shoppingCartId}")
    public ResponseEntity<ShoppingCartResponseDto> patchShoppingCart(@Auth AuthUser authUser, @PathVariable("shoppingCartId") Long shoppingCartId,@RequestBody ShoppingCartPatchRequestDto shoppingCartPatchRequestDto) {
        ShoppingCartResponseDto shoppingCartResponseDto = shoppingCartService.patchShoppingCart(authUser, shoppingCartId, shoppingCartPatchRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartResponseDto);
    }

    @DeleteMapping("/{shoppingCartId}")
    public void deleteShoppingCart(@Auth AuthUser authUser, @PathVariable("shoppingCartId") Long shoppingCartId) {
        shoppingCartService.deleteShoppingCart(authUser, shoppingCartId);
    }


}
