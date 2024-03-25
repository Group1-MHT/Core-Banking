package com.example.banking_manager_user.controller;

import com.example.banking_manager_user.dto.UserDto;
import com.example.banking_manager_user.model.Role;
import com.example.banking_manager_user.model.User;
import com.example.banking_manager_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/manager-user-service")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/as/user/create")
//    @PreAuthorize("hasAuthority('User')")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        List<String> roleNames = userDto.getRoles();
        User createdUser = userService.createUser(userDto,roleNames);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/as/user/update/{userId}")
//    @PreAuthorize("hasAnyAuthority('Admin','Staff','User')")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User updatedUser) {
        User updated = userService.updateUser(userId, updatedUser);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/as/user/delete/{userId}")
//    @PreAuthorize("hasAnyAuthority('Admin','Staff')")
    public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/as/user/roles/{userId}")
//    @PreAuthorize("hasAnyAuthority('Admin','Staff')")
    public ResponseEntity<List<Role>> getUserRoles(@PathVariable Integer userId) {
        List<Role> userRoles = userService.getUserRoles(userId);
        return new ResponseEntity<>(userRoles, HttpStatus.OK);
    }

    @PostMapping("/as/user/grant-role/{userId}/{roleId}")
//    @PreAuthorize("hasAnyAuthority('Admin','Staff')")
    public ResponseEntity<String> grantRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        userService.grantRole(userId, roleId);
        return new ResponseEntity<>("Role granted successfully", HttpStatus.OK);
    }

    @PostMapping("/as/user/revoke-role/{userId}/{roleId}")
//    @PreAuthorize("hasAnyAuthority('Admin','Staff')")
    public ResponseEntity<String> revokeRole(@PathVariable Integer userId, @PathVariable Integer roleId) {
        userService.revokeRole(userId, roleId);
        return new ResponseEntity<>("Role revoked successfully", HttpStatus.OK);
    }
    @GetMapping("/as/user/getById/{id}")
//    @PreAuthorize("hasAnyAuthority('Admin','Staff')")
    public ResponseEntity<User> getById(@PathVariable Integer id){
        User infoUser = userService.getById(id);
        return new ResponseEntity<>(infoUser,HttpStatus.OK);
    }
}
