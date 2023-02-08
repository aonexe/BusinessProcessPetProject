package com.vorsin.businessProcess.exception;

public class StageNotFoundException extends RuntimeException{

    public StageNotFoundException(String message) {
        super(message);
    }
}
