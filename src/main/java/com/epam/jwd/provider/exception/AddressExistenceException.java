package com.epam.jwd.provider.exception;

public class AddressExistenceException extends RuntimeException {
    public AddressExistenceException() {
    }

    public AddressExistenceException(String message) {
        super(message);
    }
}
