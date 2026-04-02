package com.prince.finance.finsecure.DTO;
import com.prince.finance.finsecure.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {
    @NotBlank
    @Length(min=3,max=15,message = "username should be greater than 3 character")
    private String username;
    private Role role;
    @Email(message = "Invalid email")
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain uppercase, lowercase, number, special character and be at least 8 characters long")
    private String password;
}
