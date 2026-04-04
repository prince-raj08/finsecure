package com.prince.finance.finsecure.security;

import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.enums.Status;
import com.prince.finance.finsecure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        log.info("Loading user by email: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: {}", email);
                    return new UsernameNotFoundException(
                            "User not found with email: " + email);
                });

        boolean isLocked = user.getStatus() == Status.INACTIVE;

        if (isLocked) {
            log.warn("User account is locked: {}", email);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .accountLocked(isLocked) // ← Yeh add kiya
                .build();
    }
}