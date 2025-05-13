package com.theater.booking.dto.response;


import com.theater.booking.model.Talk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TalkResponseDTO {
    private Long id;
    private String name;
    private String dateTime;
    private String description;

    public TalkResponseDTO(Talk talk) {
        this.id = talk.getId();
        this.name = talk.getName();
        this.dateTime = talk.getDateTime().toString();
        this.description = talk.getDescription();
    }
}
