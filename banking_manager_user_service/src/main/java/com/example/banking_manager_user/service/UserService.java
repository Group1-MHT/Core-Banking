package com.example.banking_manager_user.service;

import com.example.banking_manager_user.dto.UserDto;
import com.example.banking_manager_user.entity.Role;
import com.example.banking_manager_user.entity.User;

import java.util.List;

public interface UserService {
    User createUser(UserDto userDto, List<String> name);
    User updateUser(Integer id, User updateUser);
    void deleteUser(Integer userId);
    List<Role> getUserRoles(Integer id);
    void grantRole(Integer userId, Integer roleId);
    void revokeRole(Integer userId, Integer roleId);
    User getById(Integer userId);
}
