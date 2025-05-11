package com.theater.booking.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConcertRequestDTO {
    @NotBlank(message = "name must not be blank")
    private String name;
    @NotNull(message = "dateTime must not be null")
    private LocalDateTime dateTime;
    @NotBlank(message = "description must not be blank")
    private String description;
}
