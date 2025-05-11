package com.theater.booking.dto;


import com.theater.booking.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private String eventName;
    private String bookingDate;
    private String ticketType;
    private Double ticketPrice;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.customerName = booking.getCustomer().getName();
        this.customerEmail = booking.getCustomer().getEmail();
        this.customerPhone = booking.getCustomer().getPhone();
        this.eventName = booking.getTicket().getEvent().getName();
        this.bookingDate = booking.getBookingDate().toString();
        this.ticketType = booking.getTicket().getType();
        this.ticketPrice = booking.getTicket().getPrice();
    }
}
