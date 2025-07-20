package com.marine.instamarinecore.dao;

import com.marine.instamarinecore.entity.Follow;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface FollowDAO extends GenericDAO<Follow> {

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followedId = ?1")
    long countFollowers(UUID userId);

    @Query("SELECT COUNT(f) FROM Follow f WHERE f.followerId = ?1")
    long countFollowing(UUID userId);
    
    @Query("SELECT f FROM Follow f WHERE f.followedId = ?1")
    List<Follow> findByFollowedId(UUID followedId);

    @Query("SELECT f FROM Follow f WHERE f.followerId = ?1")
    List<Follow> findByFollowerId(UUID followerId);

    boolean existsByFollowerIdAndFollowedId(UUID followerId, UUID followedId);
}
