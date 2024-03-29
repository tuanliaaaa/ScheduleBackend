package com.g11.schedule.exception;

import com.g11.schedule.dto.ResponseError;
import com.g11.schedule.exception.base.AccessDeniedException;
import com.g11.schedule.exception.base.BaseException;
import com.g11.schedule.exception.base.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class AdviceController {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseError<String>> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<>(ResponseError.of(403,"Access Denied",ex.getMessage()),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)

    public ResponseEntity<ResponseError<String>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        return new ResponseEntity<>(ResponseError.of(403,"Access Denied","Token hết hạn"),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseError<String>> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ResponseError.of(400,"Not Found",ex.getMessage()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError<Map<String ,String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(ResponseError.of(400,"validate error",errors),HttpStatus.BAD_REQUEST);
    }
}