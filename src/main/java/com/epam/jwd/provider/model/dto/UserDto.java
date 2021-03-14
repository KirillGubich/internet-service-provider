package com.epam.jwd.provider.model.dto;

import com.epam.jwd.provider.model.entity.UserRole;

import java.math.BigDecimal;
import java.util.Objects;

public class UserDto {
    private final Integer id;
    private final String login;
    private final String password;
    private final UserRole role;
    private final BigDecimal balance;
    private final Boolean active;

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Boolean getActive() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(login, userDto.login) && Objects.equals(password, userDto.password) && role == userDto.role && Objects.equals(balance, userDto.balance) && Objects.equals(active, userDto.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, balance, active);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", balance=" + balance +
                ", isActive=" + active +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer id;
        private String login;
        private String password;
        private UserRole role;
        private BigDecimal balance;
        private Boolean isActive;

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

        public Builder withBalance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder withActive(Boolean status) {
            this.isActive = status;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

    private UserDto(Builder builder) {
        this.id = builder.id;
        this.login = builder.login;
        this.password = builder.password;
        this.role = builder.role;
        this.balance = builder.balance;
        this.active = builder.isActive;
    }
}
