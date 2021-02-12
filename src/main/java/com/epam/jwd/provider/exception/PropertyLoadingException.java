package com.epam.jwd.provider.exception;

public class PropertyLoadingException extends RuntimeException {

    public PropertyLoadingException() {
    }

    public PropertyLoadingException(String message) {
        super(message);
    }

    public PropertyLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertyLoadingException(Throwable cause) {
        super(cause);
    }
}
