package com.epam.jwd.provider.exception;

public class PropertiesAbsenceException extends RuntimeException {

    public PropertiesAbsenceException() {
    }

    public PropertiesAbsenceException(String message) {
        super(message);
    }

    public PropertiesAbsenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PropertiesAbsenceException(Throwable cause) {
        super(cause);
    }
}
