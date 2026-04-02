package com.prince.finance.finsecure.controller;

import com.prince.finance.finsecure.DTO.UserDto;
import com.prince.finance.finsecure.DTO.UserResponseDto;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.services.UserServicesImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServicesImpl userServices;

    @PostMapping("/register")
    ResponseEntity<User> register(@Valid @RequestBody UserDto user)
    {
        log.info("Received registration request for: {}",user.getEmail());
        User response = userServices.register(user);
        log.info("User successfully created with ID: {}. Sending response back to client.", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDto>> getAllUser()
    {
        log.info("Received fetching list user request  ");
        List<UserResponseDto> result = userServices.getAllUsers();
        if(result.isEmpty())
        {
            log.warn("No users found in the system");
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
