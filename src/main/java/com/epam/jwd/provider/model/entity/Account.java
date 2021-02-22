package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.model.BaseEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class Account extends BaseEntity {
    private final BigDecimal balance;
    private final BigDecimal credit;

    public Account(Integer id, BigDecimal balance, BigDecimal credit) {
        super(id);
        this.balance = balance;
        this.credit = credit;
    }

    public Account(Integer id) {
        super(id);
        this.balance = new BigDecimal("0");
        this.credit = new BigDecimal("0");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(balance, account.balance) && Objects.equals(credit, account.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, credit);
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", credit=" + credit +
                '}';
    }
}
