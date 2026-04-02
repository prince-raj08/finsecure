package com.prince.finance.finsecure.controller;

import com.prince.finance.finsecure.DTO.UserDto;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.services.UserServicesImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
