package com.theater.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
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
    void testCreateBooking() throws Exception {
        BookingRequestDTO request = new BookingRequestDTO(
                "Martín", "martin@example.com", "123456789", 1L
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
}
