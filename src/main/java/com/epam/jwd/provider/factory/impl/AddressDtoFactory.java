package com.epam.jwd.provider.factory.impl;

import com.epam.jwd.provider.factory.DtoFactory;
import com.epam.jwd.provider.model.dto.AddressDto;

public enum AddressDtoFactory implements DtoFactory<AddressDto> {
    INSTANCE;

    @Override
    public AddressDto create(Object... args) {
        return new AddressDto((String) args[0], (String) args[1]);
    }
}
