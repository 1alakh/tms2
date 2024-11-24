package com.tsm_authentication.dto;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Role {
    private final Long id;
    private final String name;
    private final String description;
    private final Set<User> users = new HashSet<>();
}
