package com.epam.jwd.provider.factory.impl;

import com.epam.jwd.provider.factory.EntityFactory;
import com.epam.jwd.provider.model.entity.Tariff;

import java.math.BigDecimal;

public enum TariffFactory implements EntityFactory<Tariff> {
    INSTANCE;

    @Override
    public Tariff create(Object... args) {
        return new Tariff((Integer) args[0], (String) args[1], (BigDecimal) args[2], (Double) args[3], (Double) args[4]);
    }
}
