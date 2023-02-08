package com.vorsin.businessProcess.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({BusinessProcessNotFoundException.class, StageNotFoundException.class})
    public ResponseEntity<Object> notFoundException(Exception e) {
        log.error("Entity not found in DB", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
