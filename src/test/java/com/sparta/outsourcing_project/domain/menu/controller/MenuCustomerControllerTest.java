package com.sparta.outsourcing_project.domain.menu.controller;

import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.config.authUser.AuthUserArgumentResolver;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuListResponseDto;
import com.sparta.outsourcing_project.domain.menu.dto.response.MenuResponse;
import com.sparta.outsourcing_project.domain.menu.entity.Menu;
import com.sparta.outsourcing_project.domain.menu.enums.MenuType;
import com.sparta.outsourcing_project.domain.menu.service.MenuCustomerService;
import com.sparta.outsourcing_project.domain.store.entity.Store;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuCustomerController.class)
class MenuCustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuCustomerService menuCustomerService;

    @MockBean
    private AuthUserArgumentResolver authUserArgumentResolver;

//    @BeforeEach
//    void setup() throws Exception {
//        // AuthUserArgumentResolver의 동작을 목(mock)으로 설정
//        when(authUserArgumentResolver.resolveArgument(any(), any(), any(), any()))
//                .thenReturn(new AuthUser(2L, "test2@example.com", UserType.CUSTOMER));  // 올바른 UserType 설정
//    }

//    @Test
//    void getStoreWithMenus_Success() throws Exception {
//        Long storeId = 1L;
//        // 가상의 Store 및 Menu 객체 생성
//        AuthUser authUser = new AuthUser(1L, "test@example.com", UserType.OWNER);
//        User user = new User(authUser.getEmail(), "abcd1234!", UserType.OWNER);
//        Store store = new Store("Store1", LocalTime.of(9, 0), LocalTime.of(20, 0), 10000, user);
//        Menu menus = new Menu(store, MenuType.KOREAN, "Kimchi", 10000, "Delicious");
//
//        List<MenuResponse> menuResponses = List.of(
//                new MenuResponse(menus)
//        );
//
//        // 서비스의 동작을 목(mock)으로 설정
//        when(menuCustomerService.getStoreWithMenus(storeId, 2L)).thenReturn(menuResponses);
//
//        // 실제 테스트 수행
//        mockMvc.perform(get("/customers/stores/{storeId}/menus", storeId)
//                        .header("Authorization", "Bearer token")) // Authorization 헤더 추가
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].menuId").value(1L))
//                .andExpect(jsonPath("$[0].name").value("Kimchi"));
//    }

    @Test
    void getMenusByType_Success() throws Exception {
        Long storeId = 1L;
        String menuType = "KOREAN";
        List<MenuListResponseDto> menuList = List.of(
                new MenuListResponseDto(1L, "KOREAN", "Kimchi", 10000, "Delicious")
        );

        when(menuCustomerService.getMenusByType(storeId, menuType)).thenReturn(menuList);

        mockMvc.perform(get("/customers/stores/{storeId}/menus/types", storeId)
                        .param("menuType", menuType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].menuType").value("KOREAN"));
    }
}
