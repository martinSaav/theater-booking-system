package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class EventDeletionNotAllowedException extends BusinessException{
    public EventDeletionNotAllowedException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
