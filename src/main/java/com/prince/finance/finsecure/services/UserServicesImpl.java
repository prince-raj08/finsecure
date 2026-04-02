package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.UserDto;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.enums.Role;
import com.prince.finance.finsecure.enums.Status;
import com.prince.finance.finsecure.exceptions.CommonExceptions;
import com.prince.finance.finsecure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User register(UserDto user) {
        log.info("Starting registration process for email: {}", user.getEmail());
        if(userRepository.existsByEmail(user.getEmail()))
        {
            log.error("Registration failed: User with email {} already exists\", userDto.getEmail()");
            throw new CommonExceptions("User already exists with this email: \" + userDto.getEmail()");
        }
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getRole()!=null?user.getRole():Role.VIEWER);
        newUser.setStatus(Status.TRUE);

        log.info("New user created with role :{}", newUser);
        User result = userRepository.save(newUser);

        return result;
    }
}
