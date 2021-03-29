package com.epam.jwd.provider.exception;

public class AccountAbsenceException extends RuntimeException {

    public AccountAbsenceException() {
    }

    public AccountAbsenceException(String message) {
        super(message);
    }
}
