package com.sparta.outsourcing_project.config.jwt;

import com.sparta.outsourcing_project.domain.exception.AuthenticationFailedException;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(Long userId, String email, UserType userType) {
        Date date = new Date();

        return BEARER_PREFIX + Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("userType", userType)
                .setExpiration(new Date(date.getTime() + TOKEN_EXPIRATION_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }

    public String substringToken(String token) {
        if(StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }
        throw new AuthenticationFailedException("토큰을 찾을 수 없습니다.");
    }

    public Claims extractClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
