package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.BaseDto;

import java.math.BigDecimal;
import java.util.Objects;

public class AccountDto implements BaseDto {
    private final BigDecimal balance;
    private final BigDecimal credit;

    public AccountDto(BigDecimal balance, BigDecimal credit) {
        this.balance = balance;
        this.credit = credit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return Objects.equals(balance, that.balance) && Objects.equals(credit, that.credit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(balance, credit);
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "balance=" + balance +
                ", credit=" + credit +
                '}';
    }
}
