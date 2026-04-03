package com.prince.finance.finsecure.services;

import com.prince.finance.finsecure.DTO.UserDto;
import com.prince.finance.finsecure.DTO.UserResponseDto;
import com.prince.finance.finsecure.entities.User;
import com.prince.finance.finsecure.enums.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices {
    UserResponseDto register(UserDto user);
    List<UserResponseDto> getAllUsers();
    String updateStatus(Long id, Status status);
    UserResponseDto getMyProfile();
}
