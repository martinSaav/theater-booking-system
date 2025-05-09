package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class TicketNotFoundException extends BusinessException {

    public TicketNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
