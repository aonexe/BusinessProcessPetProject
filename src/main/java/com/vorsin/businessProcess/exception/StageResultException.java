package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class StageResultException extends GeneralException {

    public StageResultException(String message, HttpStatus status) {
        super(message, status);
    }

    public StageResultException(String message) {
        super(message);
    }
}
