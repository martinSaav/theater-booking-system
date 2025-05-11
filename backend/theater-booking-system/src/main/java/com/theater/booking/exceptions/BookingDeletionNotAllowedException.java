package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class BookingDeletionNotAllowedException extends BusinessException {
    public BookingDeletionNotAllowedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
