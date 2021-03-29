package com.epam.jwd.provider.exception;

public class AddressLookupException extends RuntimeException {

    public AddressLookupException() {
    }

    public AddressLookupException(String message) {
        super(message);
    }
}
