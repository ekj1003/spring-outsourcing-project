package com.sparta.outsourcing_project.domain.user.service;

import com.sparta.outsourcing_project.config.PasswordEncoder;
import com.sparta.outsourcing_project.config.jwt.JwtUtil;
import com.sparta.outsourcing_project.domain.exception.UserRequestException;
import com.sparta.outsourcing_project.domain.exception.AuthenticationFailedException;
import com.sparta.outsourcing_project.domain.user.dto.request.LoginRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.SignupRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.response.TokenResponseDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public TokenResponseDto signup(@Valid SignupRequestDto signupRequestDto) {
        if(userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new UserRequestException("이미 존재하는 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        User savedUser = userRepository.save(new User(signupRequestDto.getEmail(), encodedPassword, UserType.of(signupRequestDto.getUserType())));

        String jwtToken = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getUserType());
        return new TokenResponseDto(jwtToken);
    }

    @Transactional
    public TokenResponseDto login(@Valid LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                AuthenticationFailedException::new
        );
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException();
        }
        String jwtToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getUserType());
        return new TokenResponseDto(jwtToken);
    }
}
