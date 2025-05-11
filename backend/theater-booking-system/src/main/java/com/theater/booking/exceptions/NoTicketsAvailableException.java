package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class NoTicketsAvailableException extends BusinessException {
    public NoTicketsAvailableException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}