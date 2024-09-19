package com.sparta.outsourcing_project.domain.user.service;

import com.sparta.outsourcing_project.config.PasswordEncoder;
import com.sparta.outsourcing_project.domain.user.dto.request.LoginRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.SignupRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.response.TokenResponseDto;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TokenResponseDto signup(SignupRequestDto signupRequestDto) {
        return null;
    }

    @Transactional
    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        return null;
    }
}
