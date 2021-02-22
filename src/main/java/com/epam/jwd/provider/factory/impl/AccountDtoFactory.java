package com.epam.jwd.provider.factory.impl;

import com.epam.jwd.provider.factory.DtoFactory;
import com.epam.jwd.provider.model.dto.AccountDto;

import java.math.BigDecimal;

public enum AccountDtoFactory implements DtoFactory<AccountDto> {
    INSTANCE;

    @Override
    public AccountDto create(Object... args) {
        return new AccountDto((BigDecimal) args[0], (BigDecimal) args[1]);
    }
}
