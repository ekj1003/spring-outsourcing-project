package com.sparta.outsourcing_project.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.outsourcing_project.domain.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    // code 발급
    // https://kauth.kakao.com/oauth/authorize?client_id=8b8794e4bea7256020086004ea54fb40&redirect_uri=http://localhost:8080/users/kakao/callback&response_type=code

    @GetMapping("/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.ok(kakaoService.kakaoLogin(code));
    }
}
