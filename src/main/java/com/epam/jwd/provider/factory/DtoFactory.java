package com.epam.jwd.provider.factory;

import com.epam.jwd.provider.model.BaseDto;

public interface DtoFactory<T extends BaseDto> {

    T create(Object... args);
}
