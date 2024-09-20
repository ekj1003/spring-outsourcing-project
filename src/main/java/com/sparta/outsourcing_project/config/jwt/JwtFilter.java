package com.sparta.outsourcing_project.config.jwt;

import com.sparta.outsourcing_project.domain.exception.JwtException;
import com.sparta.outsourcing_project.domain.exception.UnauthorizedAccessException;
import com.sparta.outsourcing_project.domain.user.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;

    private static final String[] whitelist = {"/", "/users/signup", "/users/login"};

    private boolean isLoginCheckPath(String url) {
        return !PatternMatchUtils.simpleMatch(whitelist, url);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String url = request.getRequestURI();

        if(!isLoginCheckPath(url)) {
            filterChain.doFilter(request, response);
            return;
        }

        String bearerJwt = request.getHeader("Authorization");

        if(bearerJwt == null || !bearerJwt.startsWith("Bearer ")) {
            throw new JwtException("JWT 토큰이 필요합니다.");
        }

        String jwt = jwtUtil.substringToken(bearerJwt);

        try {
            Claims claims = jwtUtil.extractClaim(jwt);
            if(claims == null) {
                throw new JwtException("잘못된 JWT 토큰입니다.");
            }

            request.setAttribute("userId", Long.parseLong(claims.getSubject()));
            request.setAttribute("email", claims.get("email"));
            request.setAttribute("userType", claims.get("userType"));

            UserType userType = UserType.valueOf(claims.get("userType", String.class));

            if(url.startsWith("/customers") && !userType.equals(UserType.CUSTOMER)) {
                throw new UnauthorizedAccessException("손님 전용 페이지입니다.");
            }
            if(url.startsWith("/owners") && !userType.equals(UserType.OWNER)) {
                throw new UnauthorizedAccessException("사업자 전용 페이지입니다.");
            }

            filterChain.doFilter(request, response);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature: ", e);
            throw new JwtException("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT signature: ", e);
            throw new JwtException("Expired JWT signature.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT signature: ", e);
            throw new JwtException("Unsupported JWT signature.");
        } catch(Exception e) {
            log.error("Invalid JWT token: ", e);
            throw new JwtException("Invalid JWT token.");
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
