package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class ActionResultException extends GeneralException {

    public ActionResultException(String message, HttpStatus status) {
        super(message, status);
    }

    public ActionResultException(String message) {
        super(message);
    }
}
