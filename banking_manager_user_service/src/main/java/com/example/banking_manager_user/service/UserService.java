package com.example.banking_manager_user.service;

import com.example.banking_manager_user.dto.UserDto;
import com.example.banking_manager_user.model.Role;
import com.example.banking_manager_user.model.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto userDto);
    User updateUser(Integer id, UserDto userDto);
    void deleteUser(Integer userId);
    List<Role> getUserRoles(Integer id);
    void grantRole(Integer userId, Integer roleId);
    void revokeRole(Integer userId, Integer roleId);
    User getById(Integer userId);
}
