package com.theater.booking.dto.response;


import com.theater.booking.model.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponseDTO {
    private Long id;
    private String type;
    private Double price;
    private Integer totalStock;
    private Integer availableStock;
    private Long eventId;

    public TicketResponseDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.type = ticket.getType();
        this.price = ticket.getPrice();
        this.totalStock = ticket.getTotalStock();
        this.availableStock = ticket.getAvailableStock();
        this.eventId = ticket.getEvent().getId();
    }
}
