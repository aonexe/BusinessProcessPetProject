package com.vorsin.businessProcess.exception;

import org.springframework.http.HttpStatus;

public class ActionException extends GeneralException {

    public ActionException(String message, HttpStatus status) {
        super(message, status);
    }

    public ActionException(String message) {
        super(message);
    }
}
