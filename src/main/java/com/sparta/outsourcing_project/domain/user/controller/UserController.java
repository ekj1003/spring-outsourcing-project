package com.sparta.outsourcing_project.domain.user.controller;

import com.sparta.outsourcing_project.domain.user.dto.request.SignupRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.response.SignupResponseDto;
import com.sparta.outsourcing_project.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {

    }

}
