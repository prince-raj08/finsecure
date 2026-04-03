package com.prince.finance.finsecure.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prince.finance.finsecure.DTO.UserDto;
import com.prince.finance.finsecure.DTO.UserResponseDto;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.enums.Role;
import com.prince.finance.finsecure.enums.Status;
import com.prince.finance.finsecure.exceptions.CommonExceptions;
import com.prince.finance.finsecure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices{

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponseDto register(UserDto user) {
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
        newUser.setStatus(Status.ACTIVE);

        User result = userRepository.save(newUser);

        UserResponseDto response = objectMapper.convertValue(result,UserResponseDto.class);
        log.info("New user created with role :{}", response);

        return response;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        log.info("Fetching all users from database...");

        List<User> userList = userRepository.findAll();
        List<UserResponseDto> user = new ArrayList<>();
        for(User u : userList)
        {
            UserResponseDto dto = new UserResponseDto();
            dto.setId(u.getId());
            dto.setUsername(u.getUsername());
            dto.setEmail(u.getEmail());
            dto.setRole(u.getRole());
            dto.setStatus(u.getStatus());

            user.add(dto);
        }
        return user;
    }

    @Override
    public String updateStatus(Long id, Status status) {

        log.info("Fetching user with ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(()-> new CommonExceptions("User not found with id :"+id));
        user.setStatus(status);
        userRepository.save(user);
        log.info("User ID: {} status updated to {}", id, status);
        return "User status updated successfully";
    }

    @Override
    public UserResponseDto getMyProfile() {
        log.info("Authenticating user ");
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email).orElseThrow(()-> new CommonExceptions("user not found :"+email));
        UserResponseDto response = objectMapper.convertValue(user,UserResponseDto.class);
        log.info("User profile found :{}",response);

        return response;
    }
}
