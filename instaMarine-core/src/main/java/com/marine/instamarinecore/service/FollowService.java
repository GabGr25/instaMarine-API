package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.FollowDAO;
import com.marine.instamarinecore.entity.Follow;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class FollowService extends GenericService<Follow> {

    private final FollowDAO followDAO;

    public FollowService(FollowDAO internalDAO) {
        super(internalDAO);
        this.followDAO = internalDAO;
    }

    public long getNumberOfFollowers(UUID userId) {
        return followDAO.countFollowers(userId);
    }

    public long getNumberOfFollowing(UUID userId) {
        return followDAO.countFollowing(userId);
    }

    public List<Follow> getFollowers(UUID userId) {
        return followDAO.findByFollowedId(userId);
    }

    public List<Follow> getFollowing(UUID userId) {
        return followDAO.findByFollowerId(userId);
    }

    public boolean isFollowing(UUID followerId, UUID followedId) {
        return followDAO.existsByFollowerIdAndFollowedId(followerId, followedId);
    }
}
