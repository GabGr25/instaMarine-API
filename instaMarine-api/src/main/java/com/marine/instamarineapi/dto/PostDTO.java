package com.marine.instamarineapi.dto;

import java.time.Instant;
import java.util.UUID;

public record PostDTO(
        UUID id,
        UUID userId,
        String mediaUrl,
        String caption,
        String location,
        Instant createdAt,
        Instant updatedAt,
        long nbLike,
        boolean userLikedPost
) {
}
