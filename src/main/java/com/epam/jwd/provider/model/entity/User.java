package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.model.BaseEntity;

import java.util.Objects;

public class User extends BaseEntity {
    private final String login;
    private final String password;
    private final UserRole role;
    private final Account account;
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

    public Account getAccount() {
        return account;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) && Objects.equals(password, user.password) && role == user.role && Objects.equals(account, user.account) && status == user.status && Objects.equals(tariff, user.tariff);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, role, account, status, tariff);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", account=" + account +
                ", status=" + status +
                ", tariff=" + tariff +
                '}'; //TODO id?
    }

    public static class Builder {
        private Integer id;
        private String login;
        private String password;
        private UserRole role;
        private Account account;
        private UserStatus status;
        private Tariff tariff;

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

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

        public Builder withAccount(Account account) {
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

        public User build() {
            return new User(this);
        }
    }

    public User(Builder builder) { //todo Public?
        super(builder.id);
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;
        this.account = builder.account;
        this.status = builder.status;
        this.tariff = builder.tariff;
    }
}
