package com.epam.jwd.provider.service;

import java.util.List;
import java.util.Optional;

public interface CommonService<T> {

    List<T> findAll();

    void create(T dto);

    Optional<T> save(T dto);

    void delete(T dto);
}
