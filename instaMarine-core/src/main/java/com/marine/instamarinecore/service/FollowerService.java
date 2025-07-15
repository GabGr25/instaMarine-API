package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.FollowerDAO;
import com.marine.instamarinecore.entity.Follower;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FollowerService extends GenericService<Follower> {

    public FollowerService(FollowerDAO internalDAO) {
        super(internalDAO);
    }
}
