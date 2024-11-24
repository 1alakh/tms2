package com.tsm_user.service.serviceImpl;

import com.tsm_user.entity.Role;
import com.tsm_user.repository.RoleRepository;
import com.tsm_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    RoleServiceImpl(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }
    @Override
    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role updateRoleById(Long id, Role newRole) {
        Role role = roleRepository.findById(id).get();
        if(newRole.getDescription() != null){
            role.setDescription(newRole.getDescription());
        }
        if(newRole.getName() != null){
            role.setName(newRole.getName());
        }
        return  roleRepository.save(role);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
