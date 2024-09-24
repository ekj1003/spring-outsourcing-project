package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuPatchRequest;
import com.sparta.outsourcing_project.domain.menu.dto.request.MenuRequest;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuOwnerServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MenuOwnerService menuOwnerService;

    @Test
    void createMenu_Success() throws Exception {
        // Given
        Long storeId = 1L;
        Long userId = 1L;
        AuthUser authUser = new AuthUser(1L, "test@example.com", UserType.OWNER);
        MenuRequest request = new MenuRequest(MenuType.KOREAN.toString(), "김치", 10000, "한국의 전통 음식", List.of());
        User user = new User(authUser.getEmail(), "abcd1234!", UserType.OWNER);

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(user, userId);

        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, user);
        Menu menu = new Menu(store, MenuType.of(request.getMenuType()), request.getName(), request.getPrice(), request.getDescription());

        given(menuRepository.save(any())).willReturn(menu);
        given(storeRepository.findById(storeId)).willReturn(Optional.of(store));
        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // When
        MenuResponse response = menuOwnerService.createMenu(storeId, request, userId);

        // Then
        assertNotNull(response);
        assertEquals("김치", response.getName());
    }

    @Test
    void patchMenu_Success() throws Exception {
        // Given
        Long storeId = 1L;
        Long menuId = 1L;
        Long userId = 1L;
        MenuPatchRequest request = new MenuPatchRequest("KOREAN", "Kimchi", 12000, "Spicy Kimchi", false, List.of());
        User user = new User("test@test.com", "password", UserType.OWNER);
        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, user);
        Menu menu = new Menu(store, MenuType.KOREAN, "Kimchi", 10000, "Delicious");

        Field userIdField = User.class.getDeclaredField("id");
        userIdField.setAccessible(true);
        userIdField.set(user, userId);

        Field storeIdField = Store.class.getDeclaredField("id");
        storeIdField.setAccessible(true);
        storeIdField.set(store, storeId);

        Field menuIdField = Menu.class.getDeclaredField("id");
        menuIdField.setAccessible(true);
        menuIdField.set(menu, menuId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));

        // When
        MenuResponse response = menuOwnerService.patchMenu(storeId, menuId, request, userId);

        // Then
        assertNotNull(response);
        assertEquals(12000, response.getPrice());
    }
}