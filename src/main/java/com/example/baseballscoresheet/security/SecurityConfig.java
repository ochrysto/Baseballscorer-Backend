package com.example.baseballscoresheet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Ref: <a href="https://www.baeldung.com/spring-security-oauth-resource-server">Spring Security OAuth</a>
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    public SecurityFilterChain resourceServerFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(new AntPathRequestMatcher("/protected"))
                .hasRole("user")
                .requestMatchers(new AntPathRequestMatcher("/"))
                .permitAll()
                .requestMatchers(new AntPathRequestMatcher("/public"))
                .permitAll()
                .anyRequest()
                .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())
                ));
        return http.build();
    }
}