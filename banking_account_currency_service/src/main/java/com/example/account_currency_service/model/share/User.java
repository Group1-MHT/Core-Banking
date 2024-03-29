package com.example.account_currency_service.model.share;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long userId;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private LocalDateTime createdAt;
    private Set<Role> roles = new HashSet<>();
}
