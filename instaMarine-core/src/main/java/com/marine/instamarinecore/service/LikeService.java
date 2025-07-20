package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.LikeDAO;
import com.marine.instamarinecore.entity.Like;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class LikeService extends GenericService<Like> {

    private final LikeDAO likeDAO;

    public LikeService(LikeDAO internalDAO) {
        super(internalDAO);
        this.likeDAO = internalDAO;
    }

    public long getNumberOfLikesOnPost(UUID postId) {
        return likeDAO.countPostLike(postId);
    }

    public long getNumberOfPostLikedByUser(UUID userId) {
        return likeDAO.countUserLike(userId);
    }

    public List<Like> getLikesByPostId(UUID id) {
        return likeDAO.findByPostId(id);
    }

    public List<Like> getLikesByUserId(UUID id) {
        return likeDAO.findByUserId(id);
    }

    public boolean hasUserLikedPost(UUID userId, UUID postId) {
        return likeDAO.existsByUserIdAndPostId(userId, postId);
    }
}
