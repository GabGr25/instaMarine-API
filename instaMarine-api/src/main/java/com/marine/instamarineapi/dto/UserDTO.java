package com.marine.instamarineapi.dto;

import java.time.Instant;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String username,
        String bio,
        String avatarUrl,
        Instant createdAt,
        Instant updatedAt,
        long followersCount,
        long followingCount,
        Boolean isFollowed
) {
}
