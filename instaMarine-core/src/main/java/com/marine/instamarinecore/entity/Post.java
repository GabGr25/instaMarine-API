package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Post extends GenericEntity {

    private UUID userId;
    private String mediaUrl;
    private String caption;
    private String location;

    public void Post(UUID userId, String mediaUrl, String caption, String location) {
        this.userId = userId;
        this.mediaUrl = mediaUrl;
        this.caption = caption;
        this.location = location;
        this.createdAt = Instant.now();
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public String getCaption() {
        return caption;
    }

    public String getLocation() {
        return location;
    }
}
