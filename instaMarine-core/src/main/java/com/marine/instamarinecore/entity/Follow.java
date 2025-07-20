package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "follow", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"follower_id", "followed_id"})
})
public class Follow extends GenericEntity {

    private UUID followedId; // la personne suivie
    private UUID followerId; // le suiveur

    protected Follow() {
    }

    public Follow(UUID followedId, UUID followerId) {
        this.followedId = followedId;
        this.followerId = followerId;
        this.createdAt = Instant.now();
    }

    public UUID getFollowedId() {
        return followedId;
    }

    public UUID getFollowerId() {
        return followerId;
    }
}
