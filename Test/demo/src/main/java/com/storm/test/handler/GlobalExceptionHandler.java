package com.storm.test.handler;

import com.storm.test.exceptions.ObjectNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ObjectNotValidException.class)
    public ResponseEntity<?> handleException(ObjectNotValidException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getErrorMessages());

    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());

    }
}
