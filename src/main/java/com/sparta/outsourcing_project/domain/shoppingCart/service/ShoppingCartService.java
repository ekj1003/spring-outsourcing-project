package com.sparta.outsourcing_project.domain.shoppingCart.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.exception.*;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartPatchRequestDto;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartRequestDto;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartResponseDto;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.shoppingCart.repository.ShoppingCartRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public ShoppingCartResponseDto createShoppingCart(AuthUser authUser, ShoppingCartRequestDto shoppingCartRequestDto) {
        // 같은 내용으로 쇼핑카트에 있는지 확인하기
        if(shoppingCartRepository.findByUserIdAndMenuId(authUser.getId(), shoppingCartRequestDto.getMenuId()) != null) {
            throw new AlreadyExistsException();
        }

        User user = userRepository.findById(authUser.getId()).orElseThrow(() -> new UnauthorizedAccessException("사용자를 찾을 수 없습니다."));
        Store store = storeRepository.findById(shoppingCartRequestDto.getStoreId()).orElseThrow(CannotFindStoreException::new);
        Menu menu = menuRepository.findById(shoppingCartRequestDto.getMenuId()).orElseThrow(CannotFindMenuException::new);

        // 음식점에 있는 메뉴가 아닌 경우 오류를 발생
        if (menu.getStore().getId() != store.getId()) {
            throw new CannotFindMenuException();
        }

        int quantity = shoppingCartRequestDto.getQuantity();
        int price = menu.getPrice();

        List<ShoppingCart> alreadyShoppingCart = shoppingCartRepository.findAllByUserId(user.getId());
        // 장바구니가 처음이라면 생성하기
        if (alreadyShoppingCart.isEmpty()) {
            ShoppingCart shoppingCart = new ShoppingCart(user, store, menu, quantity, price);
            ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
            return new ShoppingCartResponseDto(savedShoppingCart);
        } else {
            // 이미 장바구니가 있다면 음식점이 같은지 확인하기
            if (alreadyShoppingCart.get(0).getStore().getId() == store.getId()) {
                ShoppingCart shoppingCart = new ShoppingCart(user, store, menu, quantity, price);
                ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
                return new ShoppingCartResponseDto(savedShoppingCart);
            } else {
                // 기존 장바구니에 있는 상품들의 음식점과 새로 등록하려고 하는 음식의 음식점이 다르다면 기존에 있는 장바구니 리스트를 모두 삭제 후 저장하기
                shoppingCartRepository.deleteAllByUserId(user.getId());
                ShoppingCart shoppingCart = new ShoppingCart(user, store, menu, quantity, price);
                ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
                return new ShoppingCartResponseDto(savedShoppingCart);
            }
        }
    }

    public List<ShoppingCartResponseDto> getAllShoppringCart(AuthUser authUser) {
        return shoppingCartRepository.findAllByUserId(authUser.getId()).stream().map(ShoppingCartResponseDto::new).toList();
    }

    @Transactional
    public ShoppingCartResponseDto patchShoppingCart(AuthUser authUser, Long shoppingCartId, ShoppingCartPatchRequestDto shoppingCartPatchRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(CannotFindShoppingCartException::new);

        ShoppingCart patchShoppingCart = shoppingCart.patch(shoppingCartPatchRequestDto.getQuantity());

        return new ShoppingCartResponseDto(patchShoppingCart);
    }

    @Transactional
    public void deleteShoppingCart(AuthUser authUser, Long shoppingCartId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId).orElseThrow(CannotFindShoppingCartException::new);
        if (authUser.getId() != shoppingCart.getUser().getId()) {
            throw new CannotFindShoppingCartException();
        }
        shoppingCartRepository.deleteById(shoppingCartId);
    }
}
