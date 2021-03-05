package com.epam.jwd.provider.model.entity;

public enum SubscriptionStatus {
    REQUESTED(1),
    APPROVED(2),
    SUSPENDED(3),
    DENIED(4);

    private final Integer id;

    SubscriptionStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static SubscriptionStatus of(String name) {
        for (SubscriptionStatus status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return REQUESTED;
    }
}
