package com.emp.management.exception;

import com.emp.management.dto.ErrorDetails;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorDetails> handleDuplicateResourceException(
            DuplicateResourceException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(
            BadRequestException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolation(
            DataIntegrityViolationException ex, WebRequest request) {
        String msg = "Database integrity constraint violation: " + (ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage());
        ErrorDetails error = new ErrorDetails(msg, request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ErrorDetails errorDetails = new ErrorDetails("Input validation failed", request.getDescription(false), errors);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(
            Exception ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                "An unexpected internal server error occurred: " + ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
