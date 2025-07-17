package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.UserDAO;
import com.marine.instamarinecore.entity.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class UserService extends GenericService<User> {

    public UserService(UserDAO internalDAO) {
        super(internalDAO);
    }

    public User update(UUID id, User objectToUpdate) {
        User existingUser = internalDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Object not found with id " + id));

        existingUser.mergeFrom(objectToUpdate);
        
        return internalDAO.save(existingUser);
    }
}
