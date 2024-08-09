package com.inginterview.storemanagement.exception.handler;

import com.inginterview.storemanagement.exception.EntityAlreadyExistsException;
import com.inginterview.storemanagement.exception.EntityNotFoundException;
import com.inginterview.storemanagement.exception.UserDefinedAuthenticationException;
import com.inginterview.storemanagement.model.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        final var errors = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        final var errorResponse = ErrorResponse.builder()
                .status(BAD_REQUEST.value())
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException exception) {
        final var errorResponse = ErrorResponse.builder()
                .status(NOT_FOUND.value())
                .errors(List.of(exception.getMessage()))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEntityAlreadyExistsException(EntityAlreadyExistsException exception) {
        final var errorResponse = ErrorResponse.builder()
                .status(CONFLICT.value())
                .errors(List.of(exception.getMessage()))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(value = UserDefinedAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleUserDefinedAuthenticationException(UserDefinedAuthenticationException exception) {
        final var errorResponse = ErrorResponse.builder()
                .status(UNAUTHORIZED.value())
                .errors(List.of(exception.getMessage()))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }
}
