package com.epam.jwd.provider.factory;

import com.epam.jwd.provider.model.BaseEntity;

/**
 * <code>EntityFactory</code> - factory method implementation.
 * Allows to encapsulate the creation of base entities.
 *
 * @param <T> the type that is created by the factory This type must extend BaseEntity
 * @author Kirill Gubich
 */
public interface EntityFactory<T extends BaseEntity> {

    /**
     * Creates new object
     *
     * @param args values to be passed to the constructor
     * @return created object
     */
    T create(Object... args);
}
