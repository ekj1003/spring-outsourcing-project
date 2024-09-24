package com.sparta.outsourcing_project.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.config.authUser.AuthUserArgumentResolver;
import com.sparta.outsourcing_project.domain.user.dto.request.ChangePwRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.DeleteRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.LoginRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.response.TokenResponseDto;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthUserArgumentResolver authUserArgumentResolver;

    private AuthUser authUser;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).setCustomArgumentResolvers(authUserArgumentResolver).build();
        authUser = new AuthUser(1L, "email@email.com", UserType.CUSTOMER);
    }

    @Test
    void 로그인_성공() throws Exception{
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto("email@email.com", "password");
        TokenResponseDto tokenResponseDto = new TokenResponseDto("bearerToken");
        given(userService.login(any(LoginRequestDto.class))).willReturn(tokenResponseDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/users/login")
                .content(objectMapper.writeValueAsString(loginRequestDto))
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.bearerToken").value(tokenResponseDto.getBearerToken()));

    }

    @Test
    void 비밀번호_변경_성공() throws Exception{
        // given
        Long userId = 1L;
        ChangePwRequestDto dto = new ChangePwRequestDto("Aaaa1111*", "Bbbb1111*");
        given(authUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(authUser);
        doNothing().when(userService).changePassword(anyLong(), anyLong(), any(ChangePwRequestDto.class));

        // when & then
        mockMvc.perform(post("/users/{userId}/change-pw", userId)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void 계정_삭제_성공() throws Exception{
        // given
        Long userId = 1L;
        DeleteRequestDto dto = new DeleteRequestDto("email@email.com", "password1234*");
        given(authUserArgumentResolver.supportsParameter(any())).willReturn(true);
        given(authUserArgumentResolver.resolveArgument(any(), any(), any(), any())).willReturn(authUser);
        doNothing().when(userService).softDeleteAccount(anyLong(), anyLong(), any(DeleteRequestDto.class));

        // when & then
        mockMvc.perform(delete("/users/{userId}", userId)
                .content(objectMapper.writeValueAsString(dto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}