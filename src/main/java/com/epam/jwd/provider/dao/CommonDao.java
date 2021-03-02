package com.epam.jwd.provider.dao;

import com.epam.jwd.provider.model.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T extends BaseEntity> {

    void create(T entity);

    Optional<T> read(T entity);

    Optional<List<T>> readAll();

    Optional<T> update(T entity);

    void delete(T entity);
}
