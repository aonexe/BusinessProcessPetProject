package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class BusinessProcessException extends GeneralException {

    public BusinessProcessException(String message, HttpStatus status) {
        super(message, status);
    }

    public BusinessProcessException(String message) {
        super(message);
    }
}
