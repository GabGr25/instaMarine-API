package com.marine.instamarinecore.dao;

import com.marine.instamarinecore.entity.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PostDAO extends GenericDAO<Post> {

    @Query("SELECT p FROM Post p WHERE p.userId = ?1")
    List<Post> findByUserId(UUID userId);
}
