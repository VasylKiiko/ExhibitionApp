package com.Kiiko.ExhibitionsApp.controller;

import com.Kiiko.ExhibitionsApp.exceptions.NoAccessToRecourseException;
import com.Kiiko.ExhibitionsApp.exceptions.ServiceException;
import com.Kiiko.ExhibitionsApp.model.Error;
import com.Kiiko.ExhibitionsApp.model.enums.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandlingController {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleConstraintViolationException(ConstraintViolationException exception){
        log.error("handle ConstraintViolationException: {}", exception.getMessage());
        return new Error(exception.getMessage(), ErrorType.VALIDATION_ERROR, LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.error("handle MethodArgumentNotValidException: {}", exception.getMessage());
        return exception.getBindingResult().getAllErrors().stream()
                .map(objectError -> new Error(objectError.getDefaultMessage(), ErrorType.VALIDATION_ERROR,
                        LocalDateTime.now()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(NoAccessToRecourseException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Error handleAccessException(NoAccessToRecourseException exception) {
        log.error("handle NoAccessToRecourseException: {}", exception.getMessage());
        return new Error(exception.getMessage(), exception.getErrorType(), LocalDateTime.now());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleServiceException(ServiceException exception) {
        log.error("handle ServiceException: {}", exception.getMessage());
        return new Error(exception.getMessage(), exception.getErrorType(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error handleException(Exception exception) {
        log.error("handle Exception {}: {}", exception.getClass(), exception.getMessage());
        return new Error(exception.getMessage(), ErrorType.FATAL_ERROR, LocalDateTime.now());
    }
}
