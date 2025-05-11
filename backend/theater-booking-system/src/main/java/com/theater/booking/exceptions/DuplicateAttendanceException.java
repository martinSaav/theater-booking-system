package com.theater.booking.exceptions;

import org.springframework.http.HttpStatus;

public class DuplicateAttendanceException extends BusinessException {
    public DuplicateAttendanceException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}