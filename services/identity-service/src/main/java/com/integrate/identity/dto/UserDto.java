package com.integrate.identity.dto;

import java.time.LocalDateTime;

public record UserDto(
        String userId,
        String name,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
