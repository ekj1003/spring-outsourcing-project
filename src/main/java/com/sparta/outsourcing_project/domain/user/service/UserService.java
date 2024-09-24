package com.sparta.outsourcing_project.domain.user.service;

import com.sparta.outsourcing_project.config.PasswordEncoder;
import com.sparta.outsourcing_project.config.jwt.JwtUtil;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.exception.UserRequestException;
import com.sparta.outsourcing_project.domain.exception.AuthenticationFailedException;
import com.sparta.outsourcing_project.domain.user.dto.request.ChangePwRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.DeleteRequestDto;
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

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                AuthenticationFailedException::new
        );
    }

    private boolean isCorrectPassword(User user, String password) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    private void validateNewPassword(String newPassword) {
        if (newPassword.length() < 8 || !newPassword.matches("^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,15}$")) {
            throw new UserRequestException("비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함합니다.");
        }
    }

    private void verifyUserId(Long authUserId, Long userId) {
        if (!Objects.equals(authUserId, userId)) {
            throw new AuthenticationFailedException("본인만 작업을 수행할 수 있습니다.");
        }
    }

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

    public TokenResponseDto login(@Valid LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(
                AuthenticationFailedException::new
        );
        if(user.getIsDeleted()) {
            throw new UnauthorizedAccessException("이미 삭제된 계정입니다.");
        }
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthenticationFailedException();
        }
        String jwtToken = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getUserType());
        return new TokenResponseDto(jwtToken);
    }

    @Transactional
    public void changePassword(Long authUserId, Long userId, ChangePwRequestDto changePwRequestDto) {
        verifyUserId(authUserId, userId);
        validateNewPassword(changePwRequestDto.getNewPassword());
        User user = getUser(authUserId);
        if(!isCorrectPassword(user, changePwRequestDto.getOldPassword())) {
            throw new AuthenticationFailedException("틀린 비밀번호입니다.");
        }
        user.changePassword(passwordEncoder.encode(changePwRequestDto.getNewPassword()));
    }

    @Transactional
    public void softDeleteAccount(Long authUserId, Long userId, DeleteRequestDto deleteRequestDto) {
        verifyUserId(authUserId, userId);
        User user = getUser(authUserId);
        if(!Objects.equals(user.getEmail(), deleteRequestDto.getEmail()) || !isCorrectPassword(user, deleteRequestDto.getPassword())) {
            throw new AuthenticationFailedException();
        }
        user.deleteAccount();
    }
}
