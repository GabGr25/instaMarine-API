package com.marine.instamarinecore.entity;

import jakarta.persistence.Entity;

import java.time.Instant;

@Entity
public class User extends GenericEntity {

    private String accountName;
    private String username;
    private String bio;
    private String email;
    private String password;
    private String avatarUrl;
    private Instant updatedAt;

    public void User(String accountName, String username, String bio, String email, String password, String avatarUrl) {
        this.accountName = accountName;
        this.username = username;
        this.bio = bio;
        this.email = email;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.createdAt = Instant.now();
    }

    public String getAccountName() {
        return accountName;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getEmail() {
        return email;
    }

    private String getPassword() {
        return password;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }
}
