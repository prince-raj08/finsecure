package com.prince.finance.finsecure.DTO;

import com.prince.finance.finsecure.enums.Role;
import com.prince.finance.finsecure.enums.Status;
import lombok.Data;

@Data
public class UserResponseDto {

    private Long Id;
    private String username;
    private Role role;
    private String email;
    private Status status;
}
