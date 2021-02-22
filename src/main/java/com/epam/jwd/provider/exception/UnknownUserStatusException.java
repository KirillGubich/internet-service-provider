package com.epam.jwd.provider.exception;

public class UnknownUserStatusException extends RuntimeException {

    public UnknownUserStatusException() {
    }

    public UnknownUserStatusException(String message) {
        super(message);
    }

    public UnknownUserStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownUserStatusException(Throwable cause) {
        super(cause);
    }
}
