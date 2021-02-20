package com.epam.jwd.provider.model;

public class UserDto {

    private final String login;
    private final String password;
    private final UserRole role;

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
        this.role = UserRole.GUEST;
    }

    public UserDto(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
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

    @Override
    public String toString() {
        return "UserDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
