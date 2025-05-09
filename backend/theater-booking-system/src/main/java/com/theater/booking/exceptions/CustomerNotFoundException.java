package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
