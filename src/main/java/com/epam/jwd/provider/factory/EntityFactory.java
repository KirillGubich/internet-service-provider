package com.epam.jwd.provider.factory;

import com.epam.jwd.provider.model.BaseEntity;

public interface EntityFactory<T extends BaseEntity> {

    T create(Object... args);
}
