package com.guavapay.order.exception.handler;

import com.guavapay.order.exception.DataNotFoundException;
import com.guavapay.order.exception.OrderException;
import com.guavapay.order.exception.response.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage(), ex);

        Error error = new Error(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                Objects.requireNonNull(ex.getBindingResult().getFieldError()).getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Error> handleDataIntegrityViolationException(OrderException ex) {
        logger.error(ex.getMessage(), ex);

        Error error = new Error();

        error.setStatus(HttpStatus.CONFLICT.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Error> handleDataNotFoundException(DataNotFoundException ex) {
        logger.error(ex.getMessage(), ex);

        Error error = new Error();
        error.setStatus(HttpStatus.NOT_FOUND.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Error> handleAccessDeniedException(AccessDeniedException ex) {
        logger.error(ex.getMessage(), ex);

        Error error = new Error();
        error.setStatus(HttpStatus.FORBIDDEN.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleUnknown(Exception ex) {
        logger.error(ex.getMessage(), ex);

        Error error = new Error();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
