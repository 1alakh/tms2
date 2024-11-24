package com.tsm_authentication.service;

import com.tsm_authentication.dto.User;

public interface UserService {
     User getUserByUsername(String username);
}
