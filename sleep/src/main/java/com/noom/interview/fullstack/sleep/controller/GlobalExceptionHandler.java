package com.noom.interview.fullstack.sleep.controller;

import com.fasterxml.jackson.databind.exc.*;
import com.noom.interview.fullstack.sleep.controller.dto.*;
import com.noom.interview.fullstack.sleep.exception.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.http.converter.*;
import org.springframework.validation.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .errors(List.of(e.getMessage()))
            .build();
    }

    @ResponseBody
    @ExceptionHandler(SleepLogAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorDTO handleSleepLogAlreadyExistsException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_ACCEPTABLE.value())
            .errors(List.of(e.getMessage()))
            .build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(e.getMessage(), e);
        List<String> errors = e.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(ErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .errors(errors)
            .build(), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(ErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .errors(List.of("Missing required parameter"))
            .build(), headers, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        String message = "Invalid parameter format";
        if(ex.getCause() instanceof InvalidFormatException ) {
            message += ": " + ((InvalidFormatException)ex.getCause()).getValue();

        }
        return new ResponseEntity<>(ErrorDTO.builder()
            .timestamp(LocalDateTime.now())
            .status(status.value())
            .errors(List.of(message))
            .build(), headers, status);
    }
}
