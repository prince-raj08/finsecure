package com.prince.finance.finsecure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 👈 Ye zaroori hai @PreAuthorize (Roles) ko chalane ke liye
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password ko hash karne ke liye
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // API testing ke liye CSRF disable karna padta hai
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/user/register").permitAll() // Registration sabke liye open hai
                        .anyRequest().authenticated() // Baaki saare endpoints ke liye login zaroori hai
                )
                .httpBasic(Customizer.withDefaults()); // Postman se testing ke liye Basic Auth enable karein

        return http.build();
    }
}