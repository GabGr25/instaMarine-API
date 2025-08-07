package com.marine.instamarineapi.mapper;

import com.marine.instamarineapi.dto.UserDTO;
import com.marine.instamarinecore.entity.User;
import com.marine.instamarinecore.service.FollowService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserMapper {

    private final FollowService followService;

    public UserMapper(FollowService followService) {
        this.followService = followService;
    }

    public UserDTO toUserDTO(UUID idRequester, User user) {
        long followersCount = followService.getNumberOfFollowers(user.getId());
        long followingCount = followService.getNumberOfFollowing(user.getId());
        Boolean isFollowing = idRequester.equals(user.getId())
                ? null
                : followService.isFollowing(idRequester, user.getId());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getBio(),
                user.getAvatarUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                followersCount,
                followingCount,
                isFollowing
        );
    }

    public UserDTO toUsersDTO(User user) {
        long followersCount = followService.getNumberOfFollowers(user.getId());
        long followingCount = followService.getNumberOfFollowing(user.getId());

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getBio(),
                user.getAvatarUrl(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                followersCount,
                followingCount,
                null
        );
    }
}
