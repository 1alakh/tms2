package com.tsm_authentication.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private final String id;
    private final String username;
    private final String email;
    private final String passwordHash;
    private final String firstName;
    private final String lastName;
    private final String role;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
