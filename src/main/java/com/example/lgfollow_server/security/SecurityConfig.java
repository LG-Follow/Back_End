package com.example.lgfollow_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 보호를 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/user/**", "/image/**", "/song/**", "/user-device/**").permitAll()  // 인증 없이 접근 가능
                        .anyRequest().authenticated()  // 다른 모든 요청은 인증 필요
                );;

        return http.build();
    }

}

