package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class StageRelationException extends GeneralException {

    public StageRelationException(String message, HttpStatus status) {
        super(message, status);
    }

    public StageRelationException(String message) {
        super(message);
    }
}
