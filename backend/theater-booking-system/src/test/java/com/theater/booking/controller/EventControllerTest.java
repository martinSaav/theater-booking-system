package com.theater.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theater.booking.dto.EventResponseDTO;
import com.theater.booking.dto.TheaterPlayRequestDTO;
import com.theater.booking.dto.TheaterPlayResponseDTO;
import com.theater.booking.model.TheaterPlay;
import com.theater.booking.model.Ticket;
import com.theater.booking.service.EventService;
import com.theater.booking.service.TheaterPlayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@WebMvcTest({EventController.class, TheaterPlayController.class})
@ActiveProfiles("test")
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private EventService eventService;

    @MockBean
    private TheaterPlayService theaterPlayService;

    @Test
    public void testCreateEvent() throws Exception {
        TheaterPlayRequestDTO request = new TheaterPlayRequestDTO(
                "Tech Talk",
                LocalDateTime.of(2025, 1, 1, 0, 0),
                "A talk about tech."
        );
        List<Ticket> tickets = new ArrayList<>();
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setId(1L);
        theaterPlay.setName("Tech Talk");
        theaterPlay.setDateTime(LocalDateTime.of(2025, 1, 1, 1, 0));
        theaterPlay.setDescription("A talk about tech.");
        theaterPlay.setTickets(tickets);

        TheaterPlayResponseDTO response = new TheaterPlayResponseDTO(theaterPlay);

        when(theaterPlayService.save(any(TheaterPlayRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/events/theater-plays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Tech Talk"));

        verify(theaterPlayService, times(1)).save(any(TheaterPlayRequestDTO.class));
    }

    @Test
    public void testGetAllEvents() throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setId(1L);
        theaterPlay.setName("Tech Talk");
        theaterPlay.setDateTime(LocalDateTime.of(2025, 1, 1, 1, 0));
        theaterPlay.setDescription("A talk about tech.");
        theaterPlay.setTickets(tickets);

        EventResponseDTO response = new EventResponseDTO(theaterPlay);

        when(eventService.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Tech Talk"));

        verify(eventService, times(1)).findAll();
    }
}
