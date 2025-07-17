package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "post_like")
public class Like extends GenericEntity {

    private UUID userId;
    private UUID postId;

    protected Like() {
    }

    public Like(UUID userId, UUID postId) {
        this.userId = userId;
        this.postId = postId;
        this.createdAt = Instant.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public UUID getPostId() {
        return postId;
    }
}
