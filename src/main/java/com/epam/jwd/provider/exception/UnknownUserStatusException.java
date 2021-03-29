package com.epam.jwd.provider.exception;

public class UnknownUserStatusException extends RuntimeException {

    public UnknownUserStatusException() {
    }

    public UnknownUserStatusException(String message) {
        super(message);
    }
}
