package com.sparta.outsourcing_project.domain.user.service;

import com.sparta.outsourcing_project.config.PasswordEncoder;
import com.sparta.outsourcing_project.config.jwt.JwtUtil;
import com.sparta.outsourcing_project.domain.exception.AuthenticationFailedException;
import com.sparta.outsourcing_project.domain.user.dto.request.ChangePwRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.DeleteRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.LoginRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.request.SignupRequestDto;
import com.sparta.outsourcing_project.domain.user.dto.response.TokenResponseDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private UserService userService;

    @Test
    void 로그인_성공() {
        // given
        LoginRequestDto dto = new LoginRequestDto("email@email.com", "password");
        User user = new User("email@email.com", "password", UserType.CUSTOMER);
        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "isDeleted", false);
        String token = "BearerToken";

        given(userRepository.findByEmail(anyString())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);
        given(jwtUtil.generateToken(anyLong(), anyString(), any(UserType.class))).willReturn(token);

        // when
        TokenResponseDto responseDto = userService.login(dto);

        // then
        assertEquals(token, responseDto.getBearerToken());
    }

    @Test
    void 계정_삭제_성공() {
        // given
        Long authUserId = 1L;
        Long userId = 1L;
        DeleteRequestDto deleteRequestDto = new DeleteRequestDto("email@email.com", "password");
        User user = new User("email@email.com", "password", UserType.CUSTOMER);
        ReflectionTestUtils.setField(user, "id", 1L);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        userService.softDeleteAccount(authUserId, userId, deleteRequestDto);

        // then
        assertEquals(user.getIsDeleted(), true);
    }

    @Test
    void 계정_삭제_실패() {
        // given
        Long authUserId = 1L;
        Long userId = 1L;
        DeleteRequestDto deleteRequestDto = new DeleteRequestDto("email@email.com", "password");
        User user = new User("email@email.com", "wrongPW", UserType.CUSTOMER);
        ReflectionTestUtils.setField(user, "id", 1L);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        // when & then
        assertThrows(AuthenticationFailedException.class,
                () -> userService.softDeleteAccount(authUserId, userId, deleteRequestDto));
    }

    @Test
    void 비밀번호_변경_성공() {
        // given
        Long authUserId = 1L;
        Long userId = 1L;
        ChangePwRequestDto changePwRequestDto = new ChangePwRequestDto("aaaa1111*", "bbbb1111*");
        User user = new User("email@email.com", "aaaa1111*", UserType.CUSTOMER);

        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        // when
        userService.changePassword(authUserId, userId, changePwRequestDto);

        // then
        assertEquals(user.getPassword(), passwordEncoder.encode(changePwRequestDto.getNewPassword()));
    }
}