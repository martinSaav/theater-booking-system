package com.theater.booking.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Email(message = "Customer email must be valid")
    private String customerEmail;

    @NotBlank(message = "Customer phone cannot be blank")
    @Pattern(regexp = "^\\+?\\d{9,15}$", message = "Customer phone must be a valid phone number")
    private String customerPhone;

    @NotNull(message = "Ticket ID cannot be null")
    private Long ticketId;
}
