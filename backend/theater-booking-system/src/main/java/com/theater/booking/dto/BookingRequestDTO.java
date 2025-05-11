package com.theater.booking.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {
    @NotBlank(message = "Customer name cannot be blank")
    private String customerName;
    @NotBlank(message = "Customer email cannot be blank")
    private String customerEmail;
    @NotBlank(message = "Customer phone cannot be blank")
    private String customerPhone;
    @NotNull(message = "Ticket ID cannot be null")
    private Long ticketId;
}
