package com.epam.jwd.provider.exception;

public class ConnectionTypeMismatchException extends RuntimeException {

    public ConnectionTypeMismatchException() {
    }

    public ConnectionTypeMismatchException(String message) {
        super(message);
    }
}
