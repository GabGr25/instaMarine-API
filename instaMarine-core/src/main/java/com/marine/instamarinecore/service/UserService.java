package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.UserDAO;
import com.marine.instamarinecore.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService extends GenericService<User> {

    public UserService(UserDAO internalDAO) {
        super(internalDAO);
    }
}
