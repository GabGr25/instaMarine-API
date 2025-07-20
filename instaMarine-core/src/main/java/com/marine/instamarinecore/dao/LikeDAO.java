package com.marine.instamarinecore.dao;

import com.marine.instamarinecore.entity.Like;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LikeDAO extends GenericDAO<Like> {

    @Query("SELECT count(l) FROM Like l WHERE l.postId = ?1")
    long countPostLike(UUID postId);

    @Query("SELECT count(l) FROM Like l WHERE l.userId = ?1")
    long countUserLike(UUID userId);

    @Query("SELECT l FROM Like l WHERE l.userId = ?1")
    List<Like> findByUserId(UUID userId);

    @Query("SELECT l FROM Like l WHERE l.postId = ?1")
    List<Like> findByPostId(UUID postId);

    boolean existsByUserIdAndPostId(UUID userId, UUID postId);
}
