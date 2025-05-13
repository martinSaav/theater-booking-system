package com.theater.booking.dto.response;

import com.theater.booking.model.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventResponseDTO {
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private String description;
    private List<TicketResponseDTO> tickets;

    public EventResponseDTO(Event event) {
        this.id = event.getId();
        this.name = event.getName();
        this.dateTime = event.getDateTime();
        this.description = event.getDescription();
        this.tickets = event.getTickets().stream()
                .map(TicketResponseDTO::new)
                .toList();
    }
}
