package com.sparta.outsourcing_project.config;

import com.sparta.outsourcing_project.config.jwt.JwtFilter;
import com.sparta.outsourcing_project.config.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {
    private final JwtUtil jwtUtil;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JwtFilter(jwtUtil));
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
