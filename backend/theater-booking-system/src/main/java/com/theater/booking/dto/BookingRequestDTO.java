package com.theater.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Long ticketId;
}
