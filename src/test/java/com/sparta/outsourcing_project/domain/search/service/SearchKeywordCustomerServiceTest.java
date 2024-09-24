package com.sparta.outsourcing_project.domain.search.service;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.repository.MenuRepository;
import com.sparta.outsourcing_project.domain.search.dto.response.SearchKeywordResponse;
import com.sparta.outsourcing_project.domain.search.entity.SearchKeyword;
import com.sparta.outsourcing_project.domain.search.repository.SearchKeywordRepository;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.store.repository.StoreRepository;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchKeywordCustomerServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private SearchKeywordRepository searchKeywordRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private SearchKeywordCustomerService searchKeywordCustomerService;

    @Test
    void searchStoresAndMenus_Success() throws Exception {
        // Given
        String keyword = "Kimchi";
        AuthUser authUser = new AuthUser(1L, "test@example.com", UserType.OWNER);
        User user = new User(authUser.getEmail(), "abcd1234!", UserType.OWNER);
        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, user);
        Menu menu = new Menu(store, MenuType.KOREAN, "Kimchi", 10000, "Delicious");

        List<Menu> menuList = new ArrayList<>();
        menuList.add(menu);

        Field menusField = Store.class.getDeclaredField("menus");
        menusField.setAccessible(true);
        menusField.set(store, menuList);

        Field storeIdField = Store.class.getDeclaredField("id");
        storeIdField.setAccessible(true);
        storeIdField.set(store, 1L);

        Field menuIdField = Menu.class.getDeclaredField("id");
        menuIdField.setAccessible(true);
        menuIdField.set(menu, 1L);

        // When
        List<Store> stores = List.of(store);
        when(storeRepository.findByStoreOrMenuNameContaining(keyword)).thenReturn(stores);

        // SearchKeywordCustomerService의 searchStoresAndMenus 메서드 호출
        List<SearchKeywordResponse> results = searchKeywordCustomerService.searchStoresAndMenus(keyword);

        // Then
        assertEquals(1, results.size());
        assertEquals("Store1", results.get(0).getStore().getName());
        assertEquals(1, results.get(0).getMenus().size());
        assertEquals("Kimchi", results.get(0).getMenus().get(0).getName());
    }



    @Test
    void getTopSearchKeywords_Success() {
        List<SearchKeyword> topKeywords = List.of(
                new SearchKeyword("Kimchi"),
                new SearchKeyword("Pizza")
        );

        when(searchKeywordRepository.findTop10ByOrderByCountDesc()).thenReturn(topKeywords);

        List<Map<String, Object>> results = searchKeywordCustomerService.getTopSearchKeywords();

        assertEquals(2, results.size());
        assertEquals("Kimchi", results.get(0).get("keyword"));
        assertEquals("Pizza", results.get(1).get("keyword"));
    }

    @Test
    void searchStoresAndMenusByType_Success() throws Exception {
        // Given
        MenuType menuType = MenuType.KOREAN;
        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, new User("owner1@example.com", "password", UserType.OWNER));

        List<Menu> menuList = new ArrayList<>();

        Field menusField = Store.class.getDeclaredField("menus");
        menusField.setAccessible(true);
        menusField.set(store, menuList);

        List<Store> stores = List.of(store);

        when(storeRepository.findByMenuType(menuType)).thenReturn(stores);

        // When
        List<SearchKeywordResponse> results = searchKeywordCustomerService.searchStoresAndMenusByType("KOREAN");

        // Then
        assertEquals(1, results.size());
        assertEquals("Store1", results.get(0).getStore().getName());
    }

}

