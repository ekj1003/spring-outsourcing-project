package com.sparta.outsourcing_project.domain.shoppingCart.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.order.entity.Order;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartPatchRequestDto;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartRequestDto;
import com.sparta.outsourcing_project.domain.shoppingCart.dto.ShoppingCartResponseDto;
import com.sparta.outsourcing_project.domain.shoppingCart.entity.ShoppingCart;
import com.sparta.outsourcing_project.domain.shoppingCart.repository.ShoppingCartRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    ShoppingCartRepository shoppingCartRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    StoreRepository storeRepository;

    @Mock
    MenuRepository menuRepository;

    @InjectMocks
    ShoppingCartService shoppingCartService;

    Long userId;
    Long storeId;
    Long orderId;
    Long menuId;
    Long shoppingCartId;

    AuthUser authUser;
    User user;
    Store store;
    Order order;
    Menu menu;
    ShoppingCart shoppingCart;

    @BeforeEach
    void setUp() {
        userId = 1L;
        storeId = 5L;
        orderId = 10L;
        menuId = 15L;
        shoppingCartId = 20L;

        authUser = new AuthUser(1L, "a@a.com", UserType.CUSTOMER);
        user = new User("a@a.com", "Aa88888888!", UserType.CUSTOMER);
        store = new Store("음식점", LocalTime.of(0, 0), LocalTime.of(23, 59), 10000, user);
        order = new Order(user, store, 10000);
        menu = new Menu(store, MenuType.KOREAN, "음식", 10000, "맛있음");
        shoppingCart = new ShoppingCart(user, store, menu, 10, 10000);

        ReflectionTestUtils.setField(user, "id", userId);
        ReflectionTestUtils.setField(store, "id", storeId);
        ReflectionTestUtils.setField(order, "id", orderId);
        ReflectionTestUtils.setField(menu, "id", menuId);
        ReflectionTestUtils.setField(shoppingCart, "id", shoppingCartId);
    }


    @Test
    void createShoppingCart() {
        // given
        ShoppingCartRequestDto shoppingCartRequestDto = new ShoppingCartRequestDto(storeId, menuId, 10);

        when(shoppingCartRepository.findByUserIdAndMenuId(anyLong(), anyLong())).thenReturn(null);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(storeRepository.findById(anyLong())).thenReturn(Optional.of(store));
        when(menuRepository.findById(anyLong())).thenReturn(Optional.of(menu));

        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(shoppingCart);
        when(shoppingCartRepository.findAllByUserId(anyLong())).thenReturn(shoppingCartList);

        when(shoppingCartRepository.save(any(ShoppingCart.class))).thenReturn(shoppingCart);

        // when
        ShoppingCartResponseDto result = shoppingCartService.createShoppingCart(authUser, shoppingCartRequestDto);

        // then
        assertNotNull(result);
        assertEquals(shoppingCart.getId(), result.getShoppingCartId());

    }

    @Test
    void getAllShoppringCart() {
        // given

        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto(shoppingCart);

        List<ShoppingCart> shoppingCartList = new ArrayList<>();
        shoppingCartList.add(shoppingCart);

        List<ShoppingCartResponseDto> shoppingCartResponseDtoList = new ArrayList<>();
        shoppingCartResponseDtoList.add(shoppingCartResponseDto);

        when(shoppingCartRepository.findAllByUserId(anyLong())).thenReturn(shoppingCartList);

        // when
        List<ShoppingCartResponseDto> result = shoppingCartService.getAllShoppringCart(authUser);

        // then
        assertNotNull(result);
        assertEquals(shoppingCartResponseDtoList.get(0).getShoppingCartId(), result.get(0).getShoppingCartId());
        assertEquals(shoppingCartResponseDtoList.get(0).getStoreId(), result.get(0).getStoreId());
        assertEquals(shoppingCartResponseDtoList.get(0).getPrice(), result.get(0).getPrice());
        assertEquals(shoppingCartResponseDtoList.get(0).getMenuId(), result.get(0).getMenuId());
    }

    @Test
    void patchShoppingCart() {
        // given
        ShoppingCartPatchRequestDto shoppingCartPatchRequestDto = new ShoppingCartPatchRequestDto();
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));

        // when
        ShoppingCartResponseDto result = shoppingCartService.patchShoppingCart(authUser, shoppingCartId, shoppingCartPatchRequestDto);

        // then
        assertNotNull(result);
        assertEquals(shoppingCart.getId(), result.getShoppingCartId());

    }

    @Test
    void deleteShoppingCart() {
        // given
        when(shoppingCartRepository.findById(anyLong())).thenReturn(Optional.of(shoppingCart));

        // when
        shoppingCartService.deleteShoppingCart(authUser, shoppingCartId);

        // then
        verify(shoppingCartRepository, times(1)).deleteById(anyLong());
    }
}