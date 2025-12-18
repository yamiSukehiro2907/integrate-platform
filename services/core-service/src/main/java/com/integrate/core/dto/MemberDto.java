package com.integrate.core.dto;

import java.util.UUID;

public record MemberDto(
        UUID userId,
        String role
        ) {
}
