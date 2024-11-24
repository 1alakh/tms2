package com.tsm_user.service;

import com.tsm_user.dto.UserResponse;
import com.tsm_user.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserResponse registerNewUser(User user);
    UserResponse getUserByUsername(String username);
    List<UserResponse> getAllUsers();

}
