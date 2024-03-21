package com.example.banking_authorization_server.dto;

import com.example.banking_authorization_server.entity.Role;

import java.util.List;
import java.util.Set;

public record UserDto (
     String username,
     String password,
     List<String> roles) {}

