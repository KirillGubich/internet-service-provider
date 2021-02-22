package com.epam.jwd.provider.factory.impl;

import com.epam.jwd.provider.factory.DtoFactory;
import com.epam.jwd.provider.model.dto.TariffDto;

import java.math.BigDecimal;

public enum TariffDtoFactory implements DtoFactory<TariffDto> {
    INSTANCE;

    @Override
    public TariffDto create(Object... args) {
        return new TariffDto((String) args[0], (BigDecimal) args[1], (Double) args[2], (Double) args[3]);
    }
}
