package com.theater.booking.service;


import com.theater.booking.dto.EventResponseDTO;
import com.theater.booking.interfaces.IEventService;
import com.theater.booking.model.Event;
import com.theater.booking.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EventService implements IEventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public List<EventResponseDTO> findAll() {
        return eventRepository.findAll().stream()
                .map(EventResponseDTO::new)
                .toList();
    }

    @Override
    public List<EventResponseDTO> findAllAvailable() {
        return eventRepository.findAll().stream()
                .map(EventResponseDTO::new)
                .filter(
                        event -> event.getTickets().stream()
                                .anyMatch(ticket -> ticket.getAvailableStock() > 0)
                )
                .toList();
    }


    @Override
    public EventResponseDTO findById(Long id) {
        Event event = findByIdAux(id);
        return new EventResponseDTO(event);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Event event = findByIdAux(id);
        if (!event.getTickets().isEmpty()) {
            throw new IllegalStateException("Cannot delete event with associated tickets");
        }
        eventRepository.delete(event);
        return true;
    }

    private Event findByIdAux(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Event not found"));
    }
}