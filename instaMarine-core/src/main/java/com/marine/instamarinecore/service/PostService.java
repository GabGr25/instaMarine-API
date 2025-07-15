package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.PostDAO;
import com.marine.instamarinecore.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostService extends GenericService<Post> {

    public PostService(PostDAO internalDAO) {
        super(internalDAO);
    }
}
