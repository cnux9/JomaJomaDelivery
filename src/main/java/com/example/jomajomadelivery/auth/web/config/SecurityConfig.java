package com.example.jomajomadelivery.auth.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    /**
     * Spring Security 에서 모든 요청을 받을 수 있도록 설정
     * 자동으로 만들어주는 login form 비활성화 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (CSRF:악의적인 사이트에서 사용자의 인증된 세션을 악용해 요청을 보내는 공격)
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll() // 모든 요청을 인증 없이 허용
                )
                .formLogin(AbstractHttpConfigurer::disable) // 기본적으로 제공하는 로그인 페이지 비활성화
                .build();
    }

}
