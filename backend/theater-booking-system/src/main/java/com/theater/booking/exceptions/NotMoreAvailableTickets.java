package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class NotMoreAvailableTickets extends BusinessException {
    public NotMoreAvailableTickets(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
