package com.marine.instamarinecore.dao;

import com.marine.instamarinecore.entity.GenericEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface GenericDAO<T extends GenericEntity> extends JpaRepository<T, UUID> {
}
