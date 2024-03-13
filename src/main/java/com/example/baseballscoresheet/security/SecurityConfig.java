package com.example.baseballscoresheet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * This class is a configuration class for Spring Security.
 * It enables web security and configures the security filter chain for the application.
 * Ref: <a href="https://www.baeldung.com/spring-security-oauth-resource-server">Spring Security OAuth</a>
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    /**
     * This method configures the security filter chain for the application.
     * It sets up the authorization rules for different request matchers and configures the OAuth2 resource server.
     *
     * @param http the HttpSecurity object to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
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