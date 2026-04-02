package com.prince.finance.finsecure.controller;

import com.prince.finance.finsecure.DTO.LoginDto;
import com.prince.finance.finsecure.security.CustomUserDetailsService;
import com.prince.finance.finsecure.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        log.info("Login attempt for email: {}", loginDto.getEmail());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getEmail(), loginDto.getPassword()));

        } catch (Exception e) {
            log.error("Login failed for {} : {}", loginDto.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Invalid email or password");
        }

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(loginDto.getEmail());

        String role = userDetails.getAuthorities()
                .iterator().next().getAuthority();

        String token = jwtService.generateToken(loginDto.getEmail(), role);

        log.info("Login successful for: {}", loginDto.getEmail());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "email", loginDto.getEmail(),
                "role", role
        ));
    }
}