package com.theater.booking.exceptions;


import com.theater.booking.dto.ErrorDTO;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorDTO> userNotFoundException(BusinessException e) {
        ErrorDTO errorDto = ErrorDTO.builder()
                .statusCode(String.valueOf(e.getHttpStatus().value()))
                .status(e.getHttpStatus().getReasonPhrase())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorDto, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorDTO errorDto = ErrorDTO.builder()
                .statusCode("400")
                .status("Bad Request")
                .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .build();
        return new ResponseEntity<>(errorDto, HttpStatusCode.valueOf(400));
    }
}