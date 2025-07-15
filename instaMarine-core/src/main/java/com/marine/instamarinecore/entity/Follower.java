package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Follower extends GenericEntity {

    private UUID followerId;
    private UUID followingId;

    protected Follower() {}

    public Follower(UUID followerId, UUID followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
        this.createdAt = Instant.now();
    }

    public UUID getFollowerId() {
        return followerId;
    }

    public UUID getFollowingId() {
        return followingId;
    }
}
