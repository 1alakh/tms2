package com.tsm_user.controller;

import com.tsm_user.entity.Role;
import com.tsm_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    private final RoleService roleService;
    @Autowired
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PostMapping
    public Role createNewRole(@RequestBody Role role){
        return roleService.createNewRole(role);
    }
    @GetMapping
    public List<Role> getAllRoles(){
        return roleService.getAllRoles();
    }
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }
    @PatchMapping("/{id}")
    public Role updateRoleById(@PathVariable Long id, @RequestBody Role role){
        return roleService.updateRoleById(id, role);
    }
    @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable Long id){
         roleService.deleteRoleById(id);
    }

}