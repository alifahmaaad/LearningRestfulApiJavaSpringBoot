package com.belajarspringboot.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    
    @NotEmpty(message = "nama is required")
    private String nama;
    @NotEmpty(message = "email is required")
    @Email(message = "email not valid")
    private String email;
    @NotEmpty(message = "password is required")
    private String password;
    private Set<String> roles;
}
