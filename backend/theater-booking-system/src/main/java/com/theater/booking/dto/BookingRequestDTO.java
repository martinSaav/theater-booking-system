package com.theater.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    private String email;
    private String customerName;
    private String phone;
    private LocalDateTime bookingDate;
    private Long ticketId;
}
