package com.integrate.core.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProjectDto(
        UUID id,
        String name,
        String description,
        List<MemberDto> members,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
