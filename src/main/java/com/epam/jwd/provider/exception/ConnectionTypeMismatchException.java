package com.epam.jwd.provider.exception;

public class ConnectionTypeMismatchException extends RuntimeException {

    public ConnectionTypeMismatchException() {
    }

    public ConnectionTypeMismatchException(String message) {
        super(message);
    }

    public ConnectionTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConnectionTypeMismatchException(Throwable cause) {
        super(cause);
    }
}
