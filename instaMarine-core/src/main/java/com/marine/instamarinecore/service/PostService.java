package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.PostDAO;
import com.marine.instamarinecore.entity.Post;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PostService extends GenericService<Post> {

    private final PostDAO postDAO;

    public PostService(PostDAO internalDAO) {
        super(internalDAO);
        this.postDAO = internalDAO;
    }

    public Post update(UUID id, Post postUpdated) {
        Post existingPost = internalDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Post not found with id " + id));

        existingPost.mergeFrom(postUpdated);

        return internalDAO.save(existingPost);
    }

    public List<Post> getPostsByUserId(UUID userId) {
        return postDAO.findByUserId(userId);
    }
}
