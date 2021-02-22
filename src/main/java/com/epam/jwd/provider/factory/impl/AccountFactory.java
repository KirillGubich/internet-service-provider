package com.epam.jwd.provider.factory.impl;

import com.epam.jwd.provider.factory.EntityFactory;
import com.epam.jwd.provider.model.entity.Account;

import java.math.BigDecimal;

public enum AccountFactory implements EntityFactory<Account> {
    INSTANCE;

    @Override
    public Account create(Object... args) {
        return new Account((Integer) args[0], (BigDecimal) args[1], (BigDecimal) args[2]);
    }
}
