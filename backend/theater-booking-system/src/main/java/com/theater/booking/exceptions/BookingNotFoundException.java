package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class BookingNotFoundException extends BusinessException {

    public BookingNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
