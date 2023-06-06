package com.allene.lease.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final String ERROR_MESSAGE = "errorMessage";

    // handling specific exception
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public Map<String, String> handleBusinessException(ResourceNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        return errorMap;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(AlleneDataIntegrityViolationException.class)
    public Map<String, String> handleAlleneDataIntegrityViolation(AlleneDataIntegrityViolationException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, ex.getMessage());
        return errorMap;
    }

    // exception ConstraintViolation
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ConstraintViolationException.class)
    Map<String, String> handleConstraintViolation(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, exception.getMessage());
        return errorMap;
    }

    // handling global exception
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> globalExceptionHandling(Exception exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(ERROR_MESSAGE, exception.getCause().getMessage());
        return errorMap;
    }
}