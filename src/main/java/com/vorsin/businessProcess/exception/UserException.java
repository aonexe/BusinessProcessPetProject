package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class UserException extends GeneralException {

    public UserException(String message, HttpStatus status) {
        super(message, status);
    }

    public UserException(String message) {
        super(message);
    }
}
