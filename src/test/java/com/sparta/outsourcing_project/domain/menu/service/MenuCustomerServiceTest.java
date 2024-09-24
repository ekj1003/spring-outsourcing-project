package com.sparta.outsourcing_project.domain.menu.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuListResponseDto;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MenuCustomerServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuCustomerService menuCustomerService;

    @Test
    void getStoreWithMenus_Success() throws Exception {
        // Given
        Long storeId = 1L;
        Long menuId = 1L;
        AuthUser authUser = new AuthUser(1L, "test@example.com", UserType.OWNER);
        User user = new User(authUser.getEmail(), "abcd1234!", UserType.OWNER);
        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, user);
        List<Menu> menus = List.of(new Menu(store, MenuType.KOREAN, "Kimchi", 10000, "Delicious"));

        Field menuIdField = Menu.class.getDeclaredField("id");
        menuIdField.setAccessible(true);
        menuIdField.set(menus.get(0), menuId);

        when(menuRepository.findAllByStoreIdAndIsDeletedFalse(storeId)).thenReturn(menus);

        // When
        List<MenuResponse> result = menuCustomerService.getStoreWithMenus(storeId, 1L);

        // Then
        assertEquals(1, result.size());
        assertEquals("Kimchi", result.get(0).getName());
    }

    @Test
    void getMenusByType_Success() throws Exception {
        // Given
        Long storeId = 1L;
        Long menuId = 1L;
        AuthUser authUser = new AuthUser(1L, "test@example.com", UserType.OWNER);
        User user = new User(authUser.getEmail(), "abcd1234!", UserType.OWNER);
        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, user);
        String menuType = "KOREAN";
        List<Menu> menus = List.of(new Menu(store, MenuType.KOREAN, "Kimchi", 10000, "Delicious"));

        Field menuIdField = Menu.class.getDeclaredField("id");
        menuIdField.setAccessible(true);
        menuIdField.set(menus.get(0), menuId);

        when(menuRepository.findByStoreIdAndMenuTypeAndIsDeletedFalse(storeId, MenuType.KOREAN)).thenReturn(menus);

        // When
        List<MenuListResponseDto> result = menuCustomerService.getMenusByType(storeId, menuType);

        // Then
        assertEquals(1, result.size());
        assertEquals("Kimchi", result.get(0).getName());
    }
}
