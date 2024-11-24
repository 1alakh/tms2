package com.tsm_authentication.service.serviceImpl;

import com.tsm_authentication.dto.User;
import com.tsm_authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserServiceImpl implements UserService {

    private final RestTemplate restTemplate;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserByUsername(String username) {
        String url = "http://localhost:9002/api/v1/users/username/" + username;
        return restTemplate.getForObject(url, User.class);
    }
}
