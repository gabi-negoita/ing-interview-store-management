package com.inginterview.storemanagement.config;

import com.inginterview.storemanagement.exception.UserDefinedRestControllerException;
import com.inginterview.storemanagement.model.ErrorResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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

    @ExceptionHandler(value = UserDefinedRestControllerException.class)
    public ResponseEntity<ErrorResponse> handleUserDefinedException(UserDefinedRestControllerException exception) {
        final var errorResponse = ErrorResponse.builder()
                .status(NOT_FOUND.value())
                .errors(List.of(exception.getMessage()))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }
}
