package com.integrate.core.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectRequest(
        @NotBlank(message = "Name is required")
        String name,
        String description,
        String role
) {
}
