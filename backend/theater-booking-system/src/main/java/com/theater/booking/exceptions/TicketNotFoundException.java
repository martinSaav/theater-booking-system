package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends BusinessException {

    public TicketNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
