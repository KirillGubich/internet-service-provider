package com.epam.jwd.provider.exception;

public class AddressExistenceException extends RuntimeException {
    public AddressExistenceException() {
    }

    public AddressExistenceException(String message) {
        super(message);
    }

    public AddressExistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressExistenceException(Throwable cause) {
        super(cause);
    }
}
