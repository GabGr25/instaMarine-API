package com.marine.instamarinecore.dao;

import com.marine.instamarinecore.entity.User;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends GenericDAO<User> {

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
