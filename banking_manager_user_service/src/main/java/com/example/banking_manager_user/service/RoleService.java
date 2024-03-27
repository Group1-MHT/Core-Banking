package com.example.banking_manager_user.service;

import com.example.banking_manager_user.dto.RoleDto;
import com.example.banking_manager_user.model.Role;

import java.util.List;

public interface RoleService {
    public Role createRole(RoleDto roleDto);
    public Role updateRole(Integer roleId, Role updatedRole);
    public void deleteRole(Integer roleId);
    List<Role> getAllRoles();
}
