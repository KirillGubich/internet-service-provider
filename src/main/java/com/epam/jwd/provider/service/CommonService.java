package com.epam.jwd.provider.service;

import java.util.List;
import java.util.Optional;

public interface CommonService<T> {
    Optional<List<T>> findAll();

    Optional<T> save(T dto);

    void add(T dto);
}
