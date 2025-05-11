package com.theater.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.exceptions.BookingDeletionNotAllowedException;
import com.theater.booking.exceptions.BookingNotFoundException;
import com.theater.booking.exceptions.DuplicateAttendanceException;
import com.theater.booking.exceptions.NoTicketsAvailableException;
import com.theater.booking.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
@ActiveProfiles("test")
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BookingService bookingService;


    @Test
    void testGetAllBookingsEmpty() throws Exception {
        when(bookingService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        verify(bookingService, times(1)).findAll();
    }

    @Test
    void testGetAllBookingsUnexpectedError() throws Exception {
        when(bookingService.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/bookings"))
                .andExpect(status().isInternalServerError());

        verify(bookingService, times(1)).findAll();
    }

    @Test
    void testGetAllBookingsOneBooking() throws Exception {
        BookingResponseDTO booking = new BookingResponseDTO();
        booking.setId(1L);
        booking.setCustomerName("Martín");
        booking.setCustomerEmail("martin996@hotmail.com.ar");
        booking.setCustomerPhone("1168940114");
        booking.setTicketId(1L);
        booking.setTicketType("General");
        booking.setTicketPrice(2000.0);
        booking.setEventName("Java Conf");
        booking.setEventDate("2025-05-10T18:00");
        booking.setBookingDate("2025-05-10T10:00:00");
        List<BookingResponseDTO> bookings = List.of(booking);

        when(bookingService.findAll()).thenReturn(bookings);
        mockMvc.perform(get("/api/v1/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(bookingService, times(1)).findAll();
    }

    @Test
    void getAllBookings() throws Exception {
        BookingResponseDTO booking1 = new BookingResponseDTO();
        booking1.setId(1L);
        booking1.setCustomerName("Martín");
        booking1.setCustomerEmail("martin996@hotmail.com.ar");
        booking1.setCustomerPhone("1168940114");
        booking1.setTicketId(1L);
        booking1.setTicketType("General");
        booking1.setTicketPrice(2000.0);
        booking1.setEventName("Java Conf");
        booking1.setEventDate("2025-05-10T18:00");
        booking1.setBookingDate("2025-05-10T10:00:00");

        BookingResponseDTO booking2 = new BookingResponseDTO();
        booking2.setId(2L);
        booking2.setCustomerName("Raul");
        booking2.setCustomerEmail("raul12@hotmail.com");
        booking2.setCustomerPhone("1168940114");
        booking2.setTicketId(2L);
        booking2.setTicketType("Meet & Greet");
        booking2.setTicketPrice(4000.0);
        booking2.setEventName("Java Conf");
        booking2.setEventDate("2025-05-10T18:00");
        booking2.setBookingDate("2025-05-10T10:00:00");
        List<BookingResponseDTO> bookings = List.of(booking1, booking2);

        when(bookingService.findAll()).thenReturn(bookings);

        mockMvc.perform(get("/api/v1/bookings"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(bookingService, times(1)).findAll();
    }

    @Test
    void testCreateBooking() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO(
                "Martín", "martin996@hotmail.com.ar", "1168940114", 1L
        );

        BookingResponseDTO response = new BookingResponseDTO();
        response.setId(1L);
        response.setCustomerName("Martín");
        response.setCustomerEmail("martin996@hotmail.com.ar");
        response.setCustomerPhone("1168940114");
        response.setTicketId(1L);
        response.setTicketType("General");
        response.setTicketPrice(2000.0);
        response.setEventName("Java Conf");
        response.setEventDate("2025-05-10T18:00");
        response.setBookingDate("2025-05-10T10:00:00");

        when(bookingService.save(any(BookingRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.customerName").value("Martín"))
                .andExpect(jsonPath("$.customerEmail").value("martin996@hotmail.com.ar"))
                .andExpect(jsonPath("$.customerPhone").value("1168940114"))
                .andExpect(jsonPath("$.eventName").value("Java Conf"))
                .andExpect(jsonPath("$.eventDate").value("2025-05-10T18:00"))
                .andExpect(jsonPath("$.bookingDate").value("2025-05-10T10:00:00"))
                .andExpect(jsonPath("$.ticketId").value(1L))
                .andExpect(jsonPath("$.ticketType").value("General"))
                .andExpect(jsonPath("$.ticketPrice").value(2000.0));

        verify(bookingService, times(1)).save(any(BookingRequestDTO.class));
    }

    @Test
    void testCreateBookingInvalidInput() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO("", "", "", null);

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateBookingInvalidIEmail() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO("Raul", "nosoyunmail.com", "1168940114", 1L);

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateBookingInvalidPhone() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO("Raul", "raul12@hotmail.com", "1168s", 1L);

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateBookingNoTicketsAvailable() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO("Raul", "raul12@mail.com", "1123456789", 1L);

        when(bookingService.save(any(BookingRequestDTO.class)))
                .thenThrow(new NoTicketsAvailableException("No tickets available"));

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateBookingAllFieldsNull() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO(null, null, null, null);

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateBookingDuplicateAttendanceForEvent() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO("Raul", "raul12@mail.com", "1123456789", 1L);

        when(bookingService.save(any(BookingRequestDTO.class)))
                .thenThrow(new DuplicateAttendanceException("Customer already has an ticket for this event"));

        mockMvc.perform(post("/api/v1/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());

        verify(bookingService, times(1)).save(any(BookingRequestDTO.class));
    }

    @Test
    void testDeleteBooking() throws Exception {
        doNothing().when(bookingService).delete(1L);

        mockMvc.perform(delete("/api/v1/bookings/1"))
                .andExpect(status().isNoContent());

        verify(bookingService, times(1)).delete(1L);
    }

    @Test
    void testDeleteBookingNotFound() throws Exception {
        doThrow(new BookingNotFoundException("The booking with id: 1 does not exist")).when(bookingService).delete(1L);

        mockMvc.perform(delete("/api/v1/bookings/1"))
                .andExpect(status().isNotFound());

        verify(bookingService, times(1)).delete(1L);
    }

    @Test
    void testDeleteBookingNotAllowed() throws Exception {
        doThrow(new BookingDeletionNotAllowedException("Cannot cancel booking for events within 5 days")).when(bookingService).delete(1L);

        mockMvc.perform(delete("/api/v1/bookings/1"))
                .andExpect(status().isConflict());

        verify(bookingService, times(1)).delete(1L);
    }
}
