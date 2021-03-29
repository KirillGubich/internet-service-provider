package com.epam.jwd.provider.exception;

public class PropertiesAbsenceException extends RuntimeException {

    public PropertiesAbsenceException() {
    }

    public PropertiesAbsenceException(String message) {
        super(message);
    }
}
