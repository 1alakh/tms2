package com.tsm_authentication.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JsonWebTokenResponse {
    private class User{
        public String username;
        public String userId;
        public String firstName;
        public String lastName;
        public String role;
    }
    private final String token;
    private final Date expiresIn;
    private final User user = new User();

    public JsonWebTokenResponse(String token, Date expiresIn,  String userId , String username, String firstName, String lastName, String role){
        this.token = token;
        this.expiresIn = expiresIn;
        user.username = username;
        user.userId = userId;
        user.firstName = firstName;
        user.lastName = lastName;
        user.role = role;
    }
}
