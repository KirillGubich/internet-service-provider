package com.epam.jwd.provider.factory;

import com.epam.jwd.provider.model.BaseDto;

/**
 * <code>DtoFactory</code> - factory method implementation.
 * Allows to encapsulate the creation of base DTOs.
 *
 * @param <T> the type that is created by the factory. This type must implement BaseDto
 * @author Kirill Gubich
 */
public interface DtoFactory<T extends BaseDto> {

    /**
     * Creates new object
     *
     * @param args values to be passed to the constructor
     * @return created object
     */
    T create(Object... args);
}
