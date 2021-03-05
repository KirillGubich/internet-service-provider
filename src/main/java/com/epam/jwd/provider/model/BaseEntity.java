package com.epam.jwd.provider.model;

public abstract class BaseEntity {
    private final Integer id;

    public BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    //todo overrides
}
