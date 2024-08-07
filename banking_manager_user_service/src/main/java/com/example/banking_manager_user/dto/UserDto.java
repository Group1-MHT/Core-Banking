package com.example.banking_manager_user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {
    private String username;
    private String password;
    private String email;
    private String fullName;
    private List<String> roles;
}
