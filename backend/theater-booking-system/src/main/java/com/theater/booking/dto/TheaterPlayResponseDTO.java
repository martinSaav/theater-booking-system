package com.theater.booking.dto;


import com.theater.booking.model.TheaterPlay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TheaterPlayResponseDTO {
    private Long id;
    private String name;
    private String dateTime;
    private String description;

    public TheaterPlayResponseDTO(TheaterPlay theaterPlay) {
        this.id = theaterPlay.getId();
        this.name = theaterPlay.getName();
        this.dateTime = theaterPlay.getDateTime().toString();
        this.description = theaterPlay.getDescription();
    }
}
