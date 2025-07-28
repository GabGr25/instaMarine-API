package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
public class User extends GenericEntity {

    private String username;
    private String bio;
    private String email;
    private String password;
    private String avatarUrl;
    @UpdateTimestamp
    private Instant updatedAt;

    protected User() {
    }

    public User(String username, String bio, String email, String password, String avatarUrl) {
        this.username = username;
        this.bio = bio;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void mergeFrom(User user) {
        if (user.username != null) {
            setUsername(user.username);
        }

        if (user.bio != null) {
            setBio(user.bio);
        }

        if (user.email != null) {
            setEmail(user.email);
        }

        if (user.avatarUrl != null) {
            setAvatarUrl(user.avatarUrl);
        }
    }
}
