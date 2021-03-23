package com.epam.jwd.provider.dao;

import com.epam.jwd.provider.model.BaseEntity;

import java.util.List;
import java.util.Optional;

/**
 * The layer between the application and the DBMS. CommonDao abstracts the business entities
 * of the system and reflects them on records in the database. DAO defines common
 * ways of using the connection with the database, the moments of its opening and closing
 * or fetching and returning to the connection pool.
 * The <tt>CommonDao</tt> interface provides methods to read,
 * create, update and delete entities in database.
 *
 * @param <T> the type of items stored in the database
 * @author Kirill Gubich
 */
public interface CommonDao<T extends BaseEntity> {

    /**
     * Adds an item to the database
     *
     * @param entity element to be added to database
     */
    void create(T entity);

    /**
     * Read all items from database
     *
     * @return optional of list that contains all items from database.
     * If items not found - empty optional.
     */
    Optional<List<T>> readAll();

    /**
     * Updates item in the database
     *
     * @param entity element to be updated
     * @return optional of the element previously in database
     */
    Optional<T> update(T entity);

    /**
     * Deletes item from database
     *
     * @param entity element to be deleted
     */
    void delete(T entity);
}
