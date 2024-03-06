package com.example.banking_manager_user.controller;

import com.example.banking_manager_user.entity.Role;
import com.example.banking_manager_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Role createdRole = roleService.createRole(role);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}/update")
    public ResponseEntity<Role> updateRole(@PathVariable Integer roleId, @RequestBody Role updatedRole) {
        Role updated = roleService.updateRole(roleId, updatedRole);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}/delete")
    public ResponseEntity<String> deleteRole(@PathVariable Integer roleId) {
        roleService.deleteRole(roleId);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
