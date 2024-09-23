package com.sparta.outsourcing_project.domain.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.outsourcing_project.config.PasswordEncoder;
import com.sparta.outsourcing_project.config.jwt.JwtUtil;
import com.sparta.outsourcing_project.domain.user.dto.response.KakaoUserInfoDto;
import com.sparta.outsourcing_project.domain.user.entity.User;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import com.sparta.outsourcing_project.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j(topic = "kakao login")
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KakaoService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Value("${restApi.secret.key}")
    private String restApiKey;

    private String getKakaoAccessToken(String code) throws JsonProcessingException {
        // 요청 URL
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        // HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        // HTTP Body
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", restApiKey);
        body.add("redirect_uri", "http://localhost:8080/users/kakao/callback");
        body.add("code", code);

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity.post(uri).headers(headers).body(body);

        // HTTP 요청 보내기
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        // 엑세스 토큰 파싱
        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // 요청 URL
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // HTTP header
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity.post(uri).headers(headers).body(new LinkedMultiValueMap<>());

        // HTTP 요청
        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        JsonNode jsonNode = new ObjectMapper().readTree(response.getBody());
        Long id = jsonNode.get("id").asLong();
        String email = jsonNode.get("kakao_account").get("email").asText();

        log.info("카카오 사용자 정보 - id : {}, email : {}", id, email);
        return new KakaoUserInfoDto(id, email);
    }

    private User registerKakaoUserIfNew(KakaoUserInfoDto kakaoUserInfoDto) {
        User kakaoUser = userRepository.findByKakaoId(kakaoUserInfoDto.getId()).orElse(null);

        if(kakaoUser == null) {
            User sameEmailUser = userRepository.findByEmail(kakaoUserInfoDto.getEmail()).orElse(null);
            if(sameEmailUser != null) {
                // 같은 email 가진 user 있을 경우 kakaoId 업데이트 후 반환
                kakaoUser = sameEmailUser;
                kakaoUser.updateKakaoId(kakaoUser.getKakaoId());
            } else {
                // 신규 회원가입
                String password = passwordEncoder.encode(UUID.randomUUID().toString());
                String email = kakaoUserInfoDto.getEmail();
                kakaoUser = new User(email, password, UserType.CUSTOMER, kakaoUserInfoDto.getId());
                userRepository.save(kakaoUser);
            }
        }
        return kakaoUser;
    }

    @Transactional
    public String kakaoLogin(String code) throws JsonProcessingException {
        // 인가 코드로 엑세스 토큰 요청
        String accessToken = getKakaoAccessToken(code);

        // 토큰으로 카카오 API 호출 - 엑세스 토큰으로 카카오 사용자 정보 가져오기
        KakaoUserInfoDto kakaoUserInfoDto = getKakaoUserInfo(accessToken);

        // 신규 회원일 시 회원가입
        User kakaoUser = registerKakaoUserIfNew(kakaoUserInfoDto);

        // JWT 토큰 반환
        return jwtUtil.generateToken(kakaoUser.getId(), kakaoUser.getEmail(), kakaoUser.getUserType());
    }

}
