package com.epam.jwd.provider.factory.impl;

import com.epam.jwd.provider.factory.EntityFactory;
import com.epam.jwd.provider.model.entity.Address;

public enum AddressFactory implements EntityFactory<Address> {
    INSTANCE;

    @Override
    public Address create(Object... args) {
        return new Address((Integer) args[0], (String) args[1], (String) args[2]);
    }
}
