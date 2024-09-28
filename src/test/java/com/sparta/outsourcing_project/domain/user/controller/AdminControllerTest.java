package com.sparta.outsourcing_project.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_project.domain.user.dto.response.AdminUsersDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersCountDto;
import com.sparta.outsourcing_project.domain.user.dto.response.OrdersPriceDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.service.AdminService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AdminService adminService;

    @Test
    void 유저_전체_조회_성공() throws Exception {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        AdminUsersDto dto = new AdminUsersDto(new User("email@email.com", "encodedPassword", UserType.CUSTOMER));
        Page<AdminUsersDto> pageDto = new PageImpl<>(List.of(dto), pageable, 1);
        given(adminService.getAllUsers(pageable)).willReturn(pageDto);

        // when & then
        mockMvc.perform(get("/admin/users")
                        .content(objectMapper.writeValueAsString(pageable))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Nested
    class OrdersCount {
        @Test
        void 일별_총_주문_수_조회_성공_파라미터O() throws Exception {
            // given
            LocalDate date = LocalDate.now();
            OrdersCountDto dto = new OrdersCountDto(date.toString(), 1);
            given(adminService.getOrdersCountByDate(date)).willReturn(dto);

            // when & then
            mockMvc.perform(get("/admin/orders/counts/daily")
                    .param("date", String.valueOf(date)))
                    .andExpect(status().isOk());

        }

        @Test
        void 일별_총_주문_수_조회_성공_파라미터X() throws Exception {
            // given
            LocalDate date = LocalDate.now();
            OrdersCountDto dto = new OrdersCountDto(date.toString(), 1);
            List<OrdersCountDto> dtos = new ArrayList<>();
            given(adminService.getOrdersCountedDaily()).willReturn(dtos);

            // when & then
            mockMvc.perform(get("/admin/orders/counts/daily"))
                    .andExpect(status().isOk());

        }

        @Test
        void 월별_총_주문_수_조회_성공_파라미터O() throws Exception {
            // given
            YearMonth date = YearMonth.now();
            OrdersCountDto dto = new OrdersCountDto(date.toString(), 1);
            given(adminService.getOrdersCountByMonth(date)).willReturn(dto);

            // when & then
            mockMvc.perform(get("/admin/orders/counts/monthly")
                            .param("date", String.valueOf(date)))
                    .andExpect(status().isOk());

        }

        @Test
        void 월별_총_주문_수_조회_성공_파라미터X() throws Exception {
            // given
            YearMonth date = YearMonth.now();
            OrdersCountDto dto = new OrdersCountDto(date.toString(), 1);
            List<OrdersCountDto> dtos = new ArrayList<>();
            given(adminService.getOrdersCountedDaily()).willReturn(dtos);

            // when & then
            mockMvc.perform(get("/admin/orders/counts/monthly"))
                    .andExpect(status().isOk());

        }
    }

    @Nested
    class OrdersTotalPrices {
        @Test
        void 일별_총_주문_금액_조회_성공_파라미터O() throws Exception {
            // given
            LocalDate date = LocalDate.now();
            OrdersPriceDto dto = null;
            given(adminService.getOrdersTotalPriceByDate(date)).willReturn(dto);

            // when & then
            mockMvc.perform(get("/admin/orders/total-prices/daily")
                            .param("date", String.valueOf(date)))
                    .andExpect(status().isOk());
        }

        @Test
        void 일별_총_주문_금액_조회_성공_파라미터X() throws Exception {
            // given
            LocalDate date = LocalDate.now();
            List<OrdersPriceDto> dtos = new ArrayList<>();
            given(adminService.getOrdersTotalPriceDaily()).willReturn(dtos);

            // when & then
            mockMvc.perform(get("/admin/orders/total-prices/daily"))
                    .andExpect(status().isOk());
        }

        @Test
        void 월별_총_주문_금액_조회_성공_파라미터O() throws Exception {
            // given
            YearMonth date = YearMonth.now();
            OrdersPriceDto dto = null;
            given(adminService.getOrdersTotalPriceByMonth(date)).willReturn(dto);

            // when & thenYearMonth
            mockMvc.perform(get("/admin/orders/total-prices/monthly")
                            .param("date", String.valueOf(date)))
                    .andExpect(status().isOk());
        }

        @Test
        void 월별_총_주문_금액_조회_성공_파라미터X() throws Exception {
            // given
            YearMonth date = YearMonth.now();
            List<OrdersPriceDto> dtos = new ArrayList<>();
            given(adminService.getOrdersTotalPriceMonthly()).willReturn(dtos);

            // when & then
            mockMvc.perform(get("/admin/orders/total-prices/monthly"))
                    .andExpect(status().isOk());
        }
    }
}