package com.tsm_authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class UserResponse {
    private final String userId;
    private final String username;
}
