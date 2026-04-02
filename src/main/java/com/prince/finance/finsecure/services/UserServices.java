package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.UserDto;
import com.prince.finance.finsecure.DTO.UserResponseDto;
import com.prince.finance.finsecure.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    User register(UserDto user);
    List<UserResponseDto> getAllUsers();
}
