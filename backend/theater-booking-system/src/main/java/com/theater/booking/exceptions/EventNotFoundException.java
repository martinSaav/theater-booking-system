package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
