package com.tsm_user.service.serviceImpl;

import com.tsm_user.dto.UserResponse;
import com.tsm_user.entity.Role;
import com.tsm_user.entity.User;
import com.tsm_user.exception.RoleNotFoundException;
import com.tsm_user.exception.UserAlreadyExistsException;
import com.tsm_user.repository.RoleRepository;
import com.tsm_user.repository.UserRepository;
import com.tsm_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository
            , PasswordEncoder passwordEncoder
            , RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public UserResponse registerNewUser(User user) {

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username already exists");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        Role role = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role mentioned here not found"));

        user.setRoleId(role.getId());
        User savedUser = userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setUsername(savedUser.getUsername());
        return userResponse;
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).get();
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPasswordHash(user.getPasswordHash());

        Role role = roleRepository.findById(user.getRoleId()).get();
        userResponse.setRole(role.getName());

        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        Map<Long, String> roleMap = roleRepository.findAll().stream()
                .collect(Collectors.toMap(Role::getId, Role::getName));

        return users.stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setFirstName(user.getFirstName());
            userResponse.setLastName(user.getLastName());
            userResponse.setUsername(user.getUsername());
            userResponse.setEmail(user.getEmail());

            String roleName = roleMap.get(user.getRoleId());
            if (roleName != null) {
                userResponse.setRole(roleName);
            } else {
                userResponse.setRole("Unknown"); // Fallback if role not found (optional)
            }

            return userResponse;
        }).collect(Collectors.toList());
    }
}
