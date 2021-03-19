package com.epam.jwd.provider.exception;

public class AccountAbsenceException extends RuntimeException {

    public AccountAbsenceException() {
    }

    public AccountAbsenceException(String message) {
        super(message);
    }

    public AccountAbsenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountAbsenceException(Throwable cause) {
        super(cause);
    }
}
