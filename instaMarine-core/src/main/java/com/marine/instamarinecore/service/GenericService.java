package com.marine.instamarinecore.service;

import com.marine.instamarinecore.dao.GenericDAO;
import com.marine.instamarinecore.entity.GenericEntity;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.UUID;

public abstract class GenericService<T extends GenericEntity> {

    protected final GenericDAO<T> internalDAO;

    protected GenericService(GenericDAO<T> internalDAO) {
        this.internalDAO = internalDAO;
    }

    public GenericDAO<T> getInternalDAO() {
        return internalDAO;
    }

    public void deleteAll() {
        internalDAO.deleteAll();
    }

    public void deleteById(UUID id) {
        internalDAO.deleteById(id);
    }

    public T save(final T objectToSave) {
        return internalDAO.save(objectToSave);
    }

    public long countAll() {
        return internalDAO.count();
    }

    public List<T> findAll() {
        return internalDAO.findAll();
    }

    public T findById(UUID id) {
        return internalDAO.findById(id).orElseThrow(() -> new EntityNotFoundException("Entity not found, id: " + id));
    }
}
