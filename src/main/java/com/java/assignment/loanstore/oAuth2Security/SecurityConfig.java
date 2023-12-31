package com.java.assignment.loanstore.oAuth2Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.requestMatchers("loans/login").permitAll().anyRequest().authenticated())
                .oauth2ResourceServer(oAuth2 -> oAuth2.jwt())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().build();
    }
}


