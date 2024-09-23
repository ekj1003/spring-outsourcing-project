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

    @GetMapping("/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        return ResponseEntity.ok(kakaoService.kakaoLogin(code));
    }
}
