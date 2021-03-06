package com.epam.jwd.provider.exception;

public class AddressLookupException extends RuntimeException {

    public AddressLookupException() {
    }

    public AddressLookupException(String message) {
        super(message);
    }

    public AddressLookupException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressLookupException(Throwable cause) {
        super(cause);
    }
}
