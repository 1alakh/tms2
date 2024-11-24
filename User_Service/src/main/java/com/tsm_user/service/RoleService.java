package com.tsm_user.service;

import com.tsm_user.entity.Role;

import java.util.List;

public interface RoleService {
    Role createNewRole(Role role);
    Role updateRoleById(Long id, Role role);
    void deleteRoleById(Long id);
    Role getRoleById(Long id);
    List<Role> getAllRoles();
}
