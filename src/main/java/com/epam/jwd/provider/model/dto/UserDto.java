package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.entity.Tariff;
import com.epam.jwd.provider.model.entity.UserRole;
import com.epam.jwd.provider.model.entity.UserStatus;

import java.util.Objects;

public class UserDto {
    private final String login;
    private final String password;
    private final UserRole role;
    private final AccountDto account;
    private final UserStatus status;
    private final Tariff tariff;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public AccountDto getAccount() {
        return account;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Tariff getTariff() {
        return tariff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(login, userDto.login) && Objects.equals(password, userDto.password) && role == userDto.role && Objects.equals(account, userDto.account) && status == userDto.status && Objects.equals(tariff, userDto.tariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role, account, status, tariff);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String login;
        private String password;
        private UserRole role;
        private AccountDto account;
        private UserStatus status;
        private Tariff tariff;

        public Builder withLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder withRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder withAccount(AccountDto account) {
            this.account = account;
            return this;
        }

        public Builder withStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public Builder withTariff(Tariff tariff) {
            this.tariff = tariff;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

    public UserDto(Builder builder) { //todo Public?
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;
        this.account = builder.account;
        this.status = builder.status;
        this.tariff = builder.tariff;
    }
}
