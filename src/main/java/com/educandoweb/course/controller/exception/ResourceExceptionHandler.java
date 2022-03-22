package com.educandoweb.course.controller.exception;

import com.educandoweb.course.service.exception.DatabaseException;
import com.educandoweb.course.service.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(
            ResourceNotFoundException exception, HttpServletRequest request) {

        String error = "resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError standardError = new StandardError(Instant.now(),
                status.value(),
                error,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(
            DatabaseException exception, HttpServletRequest request) {

        String error = "database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError standardError = new StandardError(Instant.now(),
                status.value(),
                error,
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity.status(status).body(standardError);
    }

}
