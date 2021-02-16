package com.epam.jwd.provider.domain;

public class UserDto {

    private final String login;
    private final String password;

    public UserDto(String login) {
        this.login = login;
        this.password = null;
    }

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
