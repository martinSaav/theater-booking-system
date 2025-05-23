package com.theater.booking.dto.response;


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
    private String eventDate;
    private String bookingDate;
    private Long ticketId;
    private String ticketType;
    private Double ticketPrice;

    public BookingResponseDTO(Booking booking) {
        this.id = booking.getId();
        this.customerName = booking.getCustomer().getName();
        this.customerEmail = booking.getCustomer().getEmail();
        this.customerPhone = booking.getCustomer().getPhone();
        this.eventName = booking.getTicket().getEvent().getName();
        this.eventDate = booking.getTicket().getEvent().getDateTime().toString();
        this.bookingDate = booking.getBookingDate().toString();
        this.ticketId = booking.getTicket().getId();
        this.ticketType = booking.getTicket().getType();
        this.ticketPrice = booking.getTicket().getPrice();
    }
}
