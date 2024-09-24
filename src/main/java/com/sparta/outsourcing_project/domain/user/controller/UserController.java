package com.sparta.outsourcing_project.domain.user.controller;

import com.sparta.outsourcing_project.config.authUser.Auth;
import com.sparta.outsourcing_project.config.authUser.AuthUser;
import com.sparta.outsourcing_project.domain.user.dto.request.ChangePwRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.DeleteRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.LoginRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.SignupRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.response.TokenResponseDto;
import com.sparta.outsourcing_project.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return new ResponseEntity<>(userService.signup(signupRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(userService.login(loginRequestDto));
    }

    @PostMapping("/{userId}/change-pw")
    public ResponseEntity<Void> changePassword(@Auth AuthUser authUser, @PathVariable Long userId, @RequestBody ChangePwRequestDto changePwRequestDto) {
        userService.changePassword(authUser.getId(), userId, changePwRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteAccount(@Auth AuthUser authUser, @PathVariable Long userId, @RequestBody DeleteRequestDto deleteRequestDto) {
        userService.softDeleteAccount(authUser.getId(), userId, deleteRequestDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
