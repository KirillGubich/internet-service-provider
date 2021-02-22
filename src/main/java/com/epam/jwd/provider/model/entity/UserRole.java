package com.epam.jwd.provider.model.entity;

public enum UserRole {
    ADMIN,
    USER,
    GUEST;

    public static UserRole of(String name) {
        for (UserRole role : values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return GUEST;
    }
}
