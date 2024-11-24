package com.tsm_user.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Role name is required")
    @Size(max = 50, message = "Role name should not exceed 50 characters")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 255, message = "Description should not exceed 255 characters")
    @Column(columnDefinition = "TEXT")
    private String description;


}
