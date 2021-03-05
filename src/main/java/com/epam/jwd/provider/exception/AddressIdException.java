package com.epam.jwd.provider.exception;

public class AddressIdException extends RuntimeException {
    public AddressIdException() {
    }

    public AddressIdException(String message) {
        super(message);
    }

    public AddressIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressIdException(Throwable cause) {
        super(cause);
    }
}
