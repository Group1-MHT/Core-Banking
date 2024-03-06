package com.example.banking_manager_user.service;

import com.example.banking_manager_user.entity.Role;

import java.util.List;

public interface RoleService {
    public Role createRole(Role role);
    public Role updateRole(Integer roleId, Role updatedRole);
    public void deleteRole(Integer roleId);
    List<Role> getAllRoles();
}
