package com.epam.jwd.provider.model;

public class User {

    private final Integer id;
    private final String login;
    private final String password;
    private final UserRole role;

    public User(Integer id, String login, String password, UserRole role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = UserRole.GUEST;
    }

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
}
