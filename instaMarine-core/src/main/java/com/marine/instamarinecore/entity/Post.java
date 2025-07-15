package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
public class Post extends GenericEntity {

    private UUID userId;
    private String mediaUrl;
    private String caption;
    private String location;
    @UpdateTimestamp
    private Instant updatedAt;

    protected Post() {}

    public Post(UUID userId, String mediaUrl, String caption, String location) {
        this.userId = userId;
        this.mediaUrl = mediaUrl;
        this.caption = caption;
        this.location = location;
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

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
