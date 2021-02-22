package com.epam.jwd.provider.dao;

import com.epam.jwd.provider.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T extends BaseEntity> {

    Optional<List<T>> findAll();

    Optional<T> save(T entity);

    void add(T entity);
}
