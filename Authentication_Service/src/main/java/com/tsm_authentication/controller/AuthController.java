package com.tsm_authentication.controller;

import com.tsm_authentication.dto.JsonWebTokenResponse;
import com.tsm_authentication.dto.LoginRequest;
import com.tsm_authentication.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/v1/auth")

public class AuthController {

    @Autowired
    private RestTemplate restTemplate;
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JsonWebTokenResponse> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        JsonWebTokenResponse jsonWebTokenResponse = authService.loginUser(loginRequest, response);

        return ResponseEntity.status(HttpStatus.OK).body(jsonWebTokenResponse);
    }
}