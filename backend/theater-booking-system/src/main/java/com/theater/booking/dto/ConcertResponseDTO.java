package com.theater.booking.dto;


import com.theater.booking.model.Concert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConcertResponseDTO {
    private Long id;
    private String name;
    private String dateTime;
    private String description;

    public ConcertResponseDTO(Concert concert) {
        this.id = concert.getId();
        this.name = concert.getName();
        this.dateTime = concert.getDateTime().toString();
        this.description = concert.getDescription();
    }
}
