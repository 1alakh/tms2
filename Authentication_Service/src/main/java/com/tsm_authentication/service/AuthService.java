package com.tsm_authentication.service;

import com.tsm_authentication.dto.JsonWebTokenResponse;
import com.tsm_authentication.dto.LoginRequest;
import com.tsm_authentication.exception.WrongCredentialException;
import com.tsm_authentication.dto.User;
import com.tsm_authentication.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService interUserService;
    @Autowired
    public AuthService(AuthenticationManager authenticationManager
            , JwtTokenProvider jwtTokenProvider, UserService interUserService) {

        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.interUserService = interUserService;
    }

    public JsonWebTokenResponse loginUser(LoginRequest loginRequest, HttpServletResponse response) {
        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            throw new WrongCredentialException("Incorrect username or password");
        }

        User user = interUserService.getUserByUsername(loginRequest.getUsername());


        ArrayList<String> roles = new ArrayList<>();
        roles.add(user.getRole());

        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), roles);

        // Create a new cookie to store the JWT
        Cookie jwtCookie = new Cookie("jwt", token);
        jwtCookie.setHttpOnly(true); // Prevent JavaScript access
        jwtCookie.setSecure(true); // Use only with HTTPS
        jwtCookie.setPath("/"); // Cookie available in the entire domain
        jwtCookie.setMaxAge(3600); // Set cookie expiration time in seconds

        // Add the cookie to the response
        response.addCookie(jwtCookie);

        return new JsonWebTokenResponse(token, jwtTokenProvider.extractExpiration(token), user.getId(), loginRequest.getUsername(),user.getFirstName(), user.getLastName(), user.getRole() );
    }
}
