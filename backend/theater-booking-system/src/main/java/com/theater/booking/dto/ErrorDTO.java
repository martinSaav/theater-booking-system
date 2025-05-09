package com.theater.booking.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDTO {
    private String statusCode;
    private String status;
    private String message;
}