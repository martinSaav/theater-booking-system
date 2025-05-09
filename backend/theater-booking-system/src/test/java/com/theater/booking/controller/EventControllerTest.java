package com.theater.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theater.booking.dto.*;
import com.theater.booking.model.Concert;
import com.theater.booking.model.Talk;
import com.theater.booking.model.TheaterPlay;
import com.theater.booking.model.Ticket;
import com.theater.booking.service.ConcertService;
import com.theater.booking.service.EventService;
import com.theater.booking.service.TalkService;
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



@WebMvcTest({EventController.class, TheaterPlayController.class, TalkController.class, ConcertController.class})
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

    @MockBean
    private TalkService talkService;

    @MockBean
    private ConcertService concertService;

    @Test
    public void testCreateTheaterPlay() throws Exception {
        TheaterPlayRequestDTO request = new TheaterPlayRequestDTO(
                "Tech Talk",
                LocalDateTime.of(2025, 1, 1, 0, 0),
                "A talk about tech."
        );
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setId(1L);
        theaterPlay.setName(request.getName());
        theaterPlay.setDateTime(request.getDateTime());
        theaterPlay.setDescription(request.getDescription());
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
    public void testCreateTalk() throws Exception {
        TalkRequestDTO request = new TalkRequestDTO("Java Conf", LocalDateTime.of(2025, 5, 10, 18, 0), "Conference");
        Talk talk = new Talk();
        talk.setId(2L);
        talk.setName(request.getName());
        talk.setDateTime(request.getDateTime());
        talk.setDescription(request.getDescription());
        TalkResponseDTO response = new TalkResponseDTO(talk);

        when(talkService.save(any(TalkRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/events/talks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Java Conf"));

        verify(talkService, times(1)).save(any(TalkRequestDTO.class));
    }

    @Test
    public void testCreateConcert() throws Exception {
        ConcertRequestDTO request = new ConcertRequestDTO("Live Rock", LocalDateTime.of(2025, 6, 15, 21, 0), "Rock concert");
        Concert concert = new Concert();
        concert.setId(3L);
        concert.setName(request.getName());
        concert.setDateTime(request.getDateTime());
        concert.setDescription(request.getDescription());
        ConcertResponseDTO response = new ConcertResponseDTO(concert);

        when(concertService.save(any(ConcertRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/events/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Live Rock"));

        verify(concertService, times(1)).save(any(ConcertRequestDTO.class));
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
