package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.LikeDAO;
import com.marine.instamarinecore.entity.Like;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LikeService extends GenericService<Like> {

    public LikeService(LikeDAO internalDAO) {
        super(internalDAO);
    }
}
