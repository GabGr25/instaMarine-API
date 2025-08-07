package com.marine.instamarineapi.mapper;

import com.marine.instamarineapi.dto.PostDTO;
import com.marine.instamarinecore.entity.Post;
import com.marine.instamarinecore.service.LikeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PostMapper {

    private final LikeService likeService;

    public PostMapper(LikeService likeService) {
        this.likeService = likeService;
    }

    public PostDTO toPostDTO(UUID idRequester, Post post) {
        long nbLike = likeService.getNumberOfLikesOnPost(post.getId());
        boolean userLikedPost = likeService.hasUserLikedPost(idRequester, post.getId());

        return new PostDTO(
                post.getId(),
                post.getUserId(),
                post.getMediaUrl(),
                post.getCaption(),
                post.getLocation(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                nbLike,
                userLikedPost
        );
    }
}
