package com.theater.booking.exceptions;


import com.theater.booking.dto.ErrorDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
}