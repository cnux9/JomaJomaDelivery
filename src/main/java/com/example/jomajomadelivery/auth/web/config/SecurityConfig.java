package com.example.jomajomadelivery.auth.web.config;

import com.example.jomajomadelivery.auth.oauth.handler.CustomAuthenticationFailureHandler;
import com.example.jomajomadelivery.auth.oauth.handler.CustomAuthenticationSuccessHandler;
import com.example.jomajomadelivery.auth.oauth.OAuth2AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final OAuth2AuthenticationService oAuth2AuthenticationService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    /**
     * Spring Security 설정
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF 보호 비활성화 (CSRF:악의적인 사이트에서 사용자의 인증된 세션을 악용해 요청을 보내는 공격)
        http
                .csrf(AbstractHttpConfigurer::disable);

        // Form 로그인 방식 비활성화
        http
                .formLogin(AbstractHttpConfigurer::disable);

        // HTTP Basic 인증 방식 비활성화
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        // oauth2
        http
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2AuthenticationService)
                        )
                );

        // 경로별 인가
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/**").hasRole("USER") // ROLE_USER 권한만 접근 가능
                        .requestMatchers("/seller/**").hasRole("SELLER") // ROLE_SELLER 권한만 접근 가능
                        .requestMatchers("/","/login/**","/signup/**" , "/css/**", "/images/**").permitAll() // 해당 요청을 인증 없이 허용
                        .anyRequest().authenticated()
                );

        // 세션 설정: STATELESS
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
