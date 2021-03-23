package com.epam.jwd.provider.service;

import java.util.List;
import java.util.Optional;

/**
 * <tt>CommonService</tt> includes the basic capabilities for interacting with
 * entities, namely create, find, edit and delete and store result to database.
 * It is a layer for interaction with DAO.
 *
 * @param <T> the type of items with which the service interacts
 * @author Kirill Gubich
 */
public interface CommonService<T> {

    /**
     * Finds all existing entities
     *
     * @return list with all existing items
     */
    List<T> findAll();

    /**
     * Creates entity and store it to database
     *
     * @param dto element to be created
     */
    void create(T dto);

    /**
     * Saves entity and returns it old state
     *
     * @param dto element to be updated
     * @return entity old state
     */
    Optional<T> save(T dto);

    /**
     * Deletes entity
     *
     * @param dto element to be deleted
     */
    void delete(T dto);
}
