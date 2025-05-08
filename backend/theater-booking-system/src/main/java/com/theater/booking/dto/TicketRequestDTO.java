package com.theater.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private String type;
    private Double price;
    private Integer totalStock;
    private Integer availableStock;
    private Long eventId;
}
