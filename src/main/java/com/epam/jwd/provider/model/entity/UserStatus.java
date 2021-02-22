package com.epam.jwd.provider.model.entity;

import com.epam.jwd.provider.exception.UnknownUserStatusException;

public enum UserStatus {
    ACTIVE,
    BLOCKED;

    public static UserStatus of(String name) {
        for (UserStatus status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        throw new UnknownUserStatusException("Unknown user status: " + name);
    }
}
