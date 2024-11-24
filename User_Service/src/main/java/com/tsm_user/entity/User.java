package com.tsm_user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(length = 36, updatable = false, nullable = false)
    private String id = UUID.randomUUID().toString();

    @NotBlank(message = "Username is required")
    @Size(max = 50, message = "Username should not exceed 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is require")
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Size(max = 50, message = "First name should not exceed 50 characters")
    @Column(name = "first_name")
    private String firstName;

    @Size(max = 50, message = "Last name should not exceed 50 characters")
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        createdAt = LocalDateTime.now();
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
    }

    @PreUpdate
    protected  void onUpdate(){
        updatedAt = LocalDateTime.now();
    }

}
