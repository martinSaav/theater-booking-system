package com.theater.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theater.booking.dto.*;
import com.theater.booking.model.Concert;
import com.theater.booking.model.Talk;
import com.theater.booking.model.TheaterPlay;
import com.theater.booking.model.Ticket;
import com.theater.booking.repository.ConcertRepository;
import com.theater.booking.repository.TalkRepository;
import com.theater.booking.repository.TheaterPlayRepository;
import com.theater.booking.service.ConcertService;
import com.theater.booking.service.EventService;
import com.theater.booking.service.TalkService;
import com.theater.booking.service.TheaterPlayService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest({EventController.class, TheaterPlayController.class, TalkController.class, ConcertController.class})
@ActiveProfiles("test")
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EventService eventService;

    @MockitoBean
    private TheaterPlayService theaterPlayService;

    @MockitoBean
    private TalkService talkService;

    @MockitoBean
    private ConcertService concertService;

    @MockitoBean
    private TheaterPlayRepository theaterPlayRepository;

    @MockitoBean
    private TalkRepository talkRepository;

    @MockitoBean
    private ConcertRepository concertRepository;


    @Test
    void TestGetAllEventsEmpty() throws Exception {
        List<EventResponseDTO> events = new ArrayList<>();
        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(eventService, times(1)).findAll();
    }

    @Test
    void testGetAllEventsUnexpectedError() throws Exception {
        when(eventService.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void TestAllEventsOneEvent() throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        List<EventResponseDTO> events = new ArrayList<>();
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setId(1L);
        theaterPlay.setName("Tech Talk");
        theaterPlay.setDateTime(LocalDateTime.of(2025, 1, 1, 1, 0));
        theaterPlay.setDescription("A talk about tech.");
        theaterPlay.setTickets(tickets);
        EventResponseDTO theaterPlayResponse = new EventResponseDTO(theaterPlay);
        events.add(theaterPlayResponse);

        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(eventService, times(1)).findAll();
    }

    @Test
    void testGetAllEvents() throws Exception {
        List<Ticket> tickets = new ArrayList<>();
        List<EventResponseDTO> events = new ArrayList<>();
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setId(1L);
        theaterPlay.setName("Tech Talk");
        theaterPlay.setDateTime(LocalDateTime.of(2025, 1, 1, 1, 0));
        theaterPlay.setDescription("A talk about tech.");
        theaterPlay.setTickets(tickets);
        EventResponseDTO theaterPlayResponse = new EventResponseDTO(theaterPlay);
        events.add(theaterPlayResponse);
        Talk talk = new Talk();
        talk.setId(2L);
        talk.setName("Java Conf");
        talk.setDateTime(LocalDateTime.of(2025, 5, 10, 18, 0));
        talk.setDescription("A conference");
        talk.setTickets(tickets);
        EventResponseDTO talkResponse = new EventResponseDTO(talk);
        events.add(talkResponse);
        Concert concert = new Concert();
        concert.setId(3L);
        concert.setName("Live Rock");
        concert.setDateTime(LocalDateTime.of(2025, 6, 15, 21, 0));
        concert.setDescription("Rock concert");
        concert.setTickets(tickets);
        EventResponseDTO concertResponse = new EventResponseDTO(concert);
        events.add(concertResponse);

        when(eventService.findAll()).thenReturn(events);

        mockMvc.perform(get("/api/v1/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

        verify(eventService, times(1)).findAll();
    }

    @Test
    void TestGetAllTheaterPlaysEmpty() throws Exception {
        List<TheaterPlayResponseDTO> theaterPlays = new ArrayList<>();
        when(theaterPlayService.findAll()).thenReturn(theaterPlays);

        mockMvc.perform(get("/api/v1/events/theater-plays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(theaterPlayService, times(1)).findAll();
    }

    @Test
    void TestGetAllTheaterPlaysUnexpectedError() throws Exception {
        when(theaterPlayService.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/events/theater-plays"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void TestGetAllTheaterPlaysOneTheaterPlay() throws Exception {
        List<TheaterPlayResponseDTO> theaterPlays = new ArrayList<>();
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setId(1L);
        theaterPlay.setName("Tech Talk");
        theaterPlay.setDateTime(LocalDateTime.of(2025, 1, 1, 0, 0));
        theaterPlay.setDescription("A talk about tech.");
        TheaterPlayResponseDTO theaterPlayResponse = new TheaterPlayResponseDTO(theaterPlay);
        theaterPlays.add(theaterPlayResponse);

        when(theaterPlayService.findAll()).thenReturn(theaterPlays);

        mockMvc.perform(get("/api/v1/events/theater-plays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(theaterPlayService, times(1)).findAll();
    }

    @Test
    void testGetAllTheaterPlays() throws Exception {
        List<TheaterPlayResponseDTO> theaterPlays = new ArrayList<>();
        TheaterPlay theaterPlay1 = new TheaterPlay();
        theaterPlay1.setId(1L);
        theaterPlay1.setName("Tech Talk");
        theaterPlay1.setDateTime(LocalDateTime.of(2025, 1, 1, 0, 0));
        theaterPlay1.setDescription("A talk about tech.");
        TheaterPlayResponseDTO theaterPlayResponse = new TheaterPlayResponseDTO(theaterPlay1);
        theaterPlays.add(theaterPlayResponse);

        TheaterPlay theaterPlay2 = new TheaterPlay();
        theaterPlay2.setId(2L);
        theaterPlay2.setName("Shakespeare Play");
        theaterPlay2.setDateTime(LocalDateTime.of(2025, 3, 10, 20, 0));
        theaterPlay2.setDescription("A Shakespeare play.");
        TheaterPlayResponseDTO theaterPlayResponse2 = new TheaterPlayResponseDTO(theaterPlay2);
        theaterPlays.add(theaterPlayResponse2);

        when(theaterPlayService.findAll()).thenReturn(theaterPlays);

        mockMvc.perform(get("/api/v1/events/theater-plays"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(theaterPlayService, times(1)).findAll();
    }

    @Test
    void TestGetAllConcertsEmpty() throws Exception {
        List<ConcertResponseDTO> concerts = new ArrayList<>();
        when(concertService.findAll()).thenReturn(concerts);

        mockMvc.perform(get("/api/v1/events/concerts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(concertService, times(1)).findAll();
    }

    @Test
    void TestGetAllConcertsUnexpectedError() throws Exception {
        when(concertService.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/events/concerts"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void TestGetAllConcertsOneConcert() throws Exception {
        List<ConcertResponseDTO> concerts = new ArrayList<>();
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setName("Live Rock");
        concert.setDateTime(LocalDateTime.of(2025, 6, 15, 21, 0));
        concert.setDescription("Rock concert");
        ConcertResponseDTO concertResponse = new ConcertResponseDTO(concert);
        concerts.add(concertResponse);

        when(concertService.findAll()).thenReturn(concerts);

        mockMvc.perform(get("/api/v1/events/concerts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(concertService, times(1)).findAll();
    }

    @Test
    void testGetAllConcerts() throws Exception {
        List<ConcertResponseDTO> concerts = new ArrayList<>();
        Concert concert1 = new Concert();
        concert1.setId(1L);
        concert1.setName("Live Rock");
        concert1.setDateTime(LocalDateTime.of(2025, 6, 15, 21, 0));
        concert1.setDescription("Rock concert");
        ConcertResponseDTO concertResponse = new ConcertResponseDTO(concert1);
        concerts.add(concertResponse);

        Concert concert2 = new Concert();
        concert2.setId(2L);
        concert2.setName("Jazz Night");
        concert2.setDateTime(LocalDateTime.of(2025, 7, 20, 19, 0));
        concert2.setDescription("Jazz concert");
        ConcertResponseDTO concertResponse2 = new ConcertResponseDTO(concert2);
        concerts.add(concertResponse2);

        when(concertService.findAll()).thenReturn(concerts);

        mockMvc.perform(get("/api/v1/events/concerts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(concertService, times(1)).findAll();
    }

    @Test
    void TestGetAllTalksEmpty() throws Exception {
        List<TalkResponseDTO> talks = new ArrayList<>();
        when(talkService.findAll()).thenReturn(talks);

        mockMvc.perform(get("/api/v1/events/talks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(0));

        verify(talkService, times(1)).findAll();
    }

    @Test
    void TestGetAllTalksUnexpectedError() throws Exception {
        when(talkService.findAll()).thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(get("/api/v1/events/talks"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void TestGetAllTalksOneTalk() throws Exception {
        List<TalkResponseDTO> talks = new ArrayList<>();
        Talk talk = new Talk();
        talk.setId(1L);
        talk.setName("Java Conf");
        talk.setDateTime(LocalDateTime.of(2025, 5, 10, 18, 0));
        talk.setDescription("A conference");
        TalkResponseDTO talkResponse = new TalkResponseDTO(talk);
        talks.add(talkResponse);

        when(talkService.findAll()).thenReturn(talks);

        mockMvc.perform(get("/api/v1/events/talks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1));

        verify(talkService, times(1)).findAll();
    }

    @Test
    void testGetAllTalks() throws Exception {
        List<TalkResponseDTO> talks = new ArrayList<>();
        Talk talk1 = new Talk();
        talk1.setId(1L);
        talk1.setName("Java Conf");
        talk1.setDateTime(LocalDateTime.of(2025, 5, 10, 18, 0));
        talk1.setDescription("A conference");
        TalkResponseDTO talkResponse = new TalkResponseDTO(talk1);
        talks.add(talkResponse);

        Talk talk2 = new Talk();
        talk2.setId(2L);
        talk2.setName("Python Workshop");
        talk2.setDateTime(LocalDateTime.of(2025, 8, 25, 14, 0));
        talk2.setDescription("A workshop about Python.");
        TalkResponseDTO talkResponse2 = new TalkResponseDTO(talk2);
        talks.add(talkResponse2);

        when(talkService.findAll()).thenReturn(talks);

        mockMvc.perform(get("/api/v1/events/talks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));

        verify(talkService, times(1)).findAll();
    }

    @Test
    void testCreateTheaterPlay() throws Exception {
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
                .andExpect(jsonPath("$.name").value("Tech Talk"))
                .andExpect(jsonPath("$.dateTime").value("2025-01-01T00:00"))
                .andExpect(jsonPath("$.description").value("A talk about tech."))
        ;

        verify(theaterPlayService, times(1)).save(any(TheaterPlayRequestDTO.class));
    }

    @Test
    void testCreateTheaterPlayInvalidInput() throws Exception {
        TheaterPlayRequestDTO invalidRequest = new TheaterPlayRequestDTO("", null, "");

        mockMvc.perform(post("/api/v1/events/theater-plays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateTalk() throws Exception {
        TalkRequestDTO request = new TalkRequestDTO("Java Conf", LocalDateTime.of(2025, 5, 10, 18, 0), "A conference");
        Talk talk = new Talk();
        talk.setId(1L);
        talk.setName(request.getName());
        talk.setDateTime(request.getDateTime());
        talk.setDescription(request.getDescription());
        TalkResponseDTO response = new TalkResponseDTO(talk);

        when(talkService.save(any(TalkRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/events/talks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java Conf"))
                .andExpect(jsonPath("$.dateTime").value("2025-05-10T18:00"))
                .andExpect(jsonPath("$.description").value("A conference"));

        verify(talkService, times(1)).save(any(TalkRequestDTO.class));
    }

    @Test
    void testCreateTalkInvalidInput() throws Exception {
        TalkRequestDTO invalidRequest = new TalkRequestDTO("", null, "");
        mockMvc.perform(post("/api/v1/events/talks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateConcert() throws Exception {
        ConcertRequestDTO request = new ConcertRequestDTO("Live Rock", LocalDateTime.of(2025, 6, 15, 21, 0), "Rock concert");
        Concert concert = new Concert();
        concert.setId(1L);
        concert.setName(request.getName());
        concert.setDateTime(request.getDateTime());
        concert.setDescription(request.getDescription());
        ConcertResponseDTO response = new ConcertResponseDTO(concert);

        when(concertService.save(any(ConcertRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/events/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Live Rock"))
                .andExpect(jsonPath("$.dateTime").value("2025-06-15T21:00"))
                .andExpect(jsonPath("$.description").value("Rock concert"));

        verify(concertService, times(1)).save(any(ConcertRequestDTO.class));
    }

    @Test
    void testCreateConcertInvalidInput() throws Exception {
        ConcertRequestDTO request = new ConcertRequestDTO("", null, "");

        mockMvc.perform(post("/api/v1/events/concerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteEvent() throws Exception {
        doNothing().when(eventService).delete(1L);

        mockMvc.perform(delete("/api/v1/events/1"))
                .andExpect(status().isNoContent());

        verify(eventService).delete(1L);
    }

    @Test
    void testDeleteEventNotFound() throws Exception {
        doThrow(new EntityNotFoundException("The event with id: 1 does not exist")).when(eventService).delete(1L);

        mockMvc.perform(delete("/api/v1/events/1"))
                .andExpect(status().isNotFound());

        verify(eventService).delete(1L);
    }

    @Test
    void testDeleteEventWithReservationsShouldFail() throws Exception {
        doThrow(new IllegalStateException("Cannot delete event with associated tickets")).when(eventService).delete(1L);

        mockMvc.perform(delete("/api/v1/events/1"))
                .andExpect(status().isConflict());

        verify(eventService).delete(1L);
    }


}
