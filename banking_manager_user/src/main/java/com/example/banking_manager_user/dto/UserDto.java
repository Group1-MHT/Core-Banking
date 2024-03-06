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
    private String name;
    private String password;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
    private List<String> roles;
}
