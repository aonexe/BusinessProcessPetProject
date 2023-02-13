package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class StageException extends GeneralException {

    public StageException(String message, HttpStatus status) {
        super(message, status);
    }

    public StageException(String message) {
        super(message);
    }
}
