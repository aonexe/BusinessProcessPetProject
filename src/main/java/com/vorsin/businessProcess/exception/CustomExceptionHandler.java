package com.vorsin.businessProcess.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> userExceptionHandler(UserException e) {
        log.error("User exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(BusinessProcessException.class)
    public ResponseEntity<Object> businessProcessExceptionHandler(BusinessProcessException e) {
        log.error("Business process exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(StageException.class)
    public ResponseEntity<Object> stageExceptionHandler(StageException e) {
        log.error("Stage exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(ActionException.class)
    public ResponseEntity<Object> actionExceptionHandler(ActionException e) {
        log.error("Action exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(ActionResultException.class)
    public ResponseEntity<Object> actionResultExceptionHandler(ActionResultException e) {
        log.error("Action result exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(StageRelationException.class)
    public ResponseEntity<Object> stageRelationExceptionHandler(StageRelationException e) {
        log.error("Stage relation exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(StageResultException.class)
    public ResponseEntity<Object> stageResultExceptionHandler(StageResultException e) {
        log.error("Stage result exception: {}", e.getMessage(), e);
        return new ResponseEntity<>(e.getMessage(), e.getStatus());
    }


}
