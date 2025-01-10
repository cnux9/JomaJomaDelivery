package com.example.jomajomadelivery.auth.web.config;

import com.example.jomajomadelivery.auth.jwt.TokenProvider;
import com.example.jomajomadelivery.auth.web.filter.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public FilterRegistrationBean<JWTFilter> jwtFilter() {
        FilterRegistrationBean<JWTFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new JWTFilter(tokenProvider));
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }
}
