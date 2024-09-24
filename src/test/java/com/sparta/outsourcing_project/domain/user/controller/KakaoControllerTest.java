package com.sparta.outsourcing_project.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_project.domain.user.service.KakaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = KakaoController.class)
class KakaoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private KakaoService kakaoService;

    @Test
    void 콜백_정상_호출() throws Exception {
        // given
        String code = "code";
        String jwtToken = "jwtToken";
        given(kakaoService.kakaoLogin(anyString())).willReturn(jwtToken);

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/kakao/callback")
                .param("code", code));

        // then
        resultActions.andExpect(status().isOk());
    }
}