package com.epam.jwd.provider.dao;

import java.util.List;
import java.util.Optional;

public interface CommonDao<T> {

    Optional<List<T>> findAll();

    Optional<T> save(T entity);

    void add(T entity);
}
