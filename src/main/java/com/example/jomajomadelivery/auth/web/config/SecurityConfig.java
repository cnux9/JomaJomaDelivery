package com.example.jomajomadelivery.auth.web.config;

import com.example.jomajomadelivery.auth.service.NaverOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Spring Security 에서 모든 요청을 받을 수 있도록 설정
     * 자동으로 만들어주는 login form 비활성화 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 (CSRF:악의적인 사이트에서 사용자의 인증된 세션을 악용해 요청을 보내는 공격)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll() // 해당 요청을 인증 없이 허용
                                .anyRequest().authenticated()
                )
                .oauth2Login(auth ->
                        auth.loginPage("/login")
                                .defaultSuccessUrl("/")
                                .failureUrl("/login")
                                .userInfoEndpoint( user ->
                                        user.userService(oAuth2UserService())
                                )
                )
                .build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new NaverOAuth2UserService();
    }

}
