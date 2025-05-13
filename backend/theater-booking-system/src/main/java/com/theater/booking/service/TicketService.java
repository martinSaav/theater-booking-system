package com.theater.booking.service;

import com.theater.booking.dto.request.TicketRequestDTO;
import com.theater.booking.dto.response.TicketResponseDTO;
import com.theater.booking.interfaces.ITicketService;
import com.theater.booking.model.Event;
import com.theater.booking.model.Ticket;
import com.theater.booking.repository.EventRepository;
import com.theater.booking.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements ITicketService {

    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    public TicketService(TicketRepository ticketRepository, EventRepository eventRepository) {
        this.ticketRepository = ticketRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<TicketResponseDTO> findAll() {
        return ticketRepository.findAll().stream()
                .map(TicketResponseDTO::new)
                .toList();
    }

    @Override
    public TicketResponseDTO findById(Long id) {
        Ticket ticket = findByIdAux(id);
        return new TicketResponseDTO(ticket);
    }

    @Transactional
    @Override
    public TicketResponseDTO save(TicketRequestDTO dto) {
        Ticket ticket = new Ticket();
        Event event = findEventById(dto.getEventId());
        ticket.setType(dto.getType());
        ticket.setPrice(dto.getPrice());
        ticket.setTotalStock(dto.getTotalStock());
        ticket.setAvailableStock(dto.getAvailableStock());
        ticket.setEvent(event);
        return new TicketResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    @Override
    public TicketResponseDTO update(Long id, TicketRequestDTO dto) {
        Ticket ticket = findByIdAux(id);
        Event event = findEventById(dto.getEventId());
        ticket.setType(dto.getType());
        ticket.setPrice(dto.getPrice());
        ticket.setTotalStock(dto.getTotalStock());
        ticket.setAvailableStock(dto.getAvailableStock());
        ticket.setEvent(event);
        return new TicketResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Ticket ticket = findByIdAux(id);
        ticketRepository.delete(ticket);
        return true;
    }

    private Ticket findByIdAux(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The ticket with id: " + id + " does not exist"));
    }

    private Event findEventById(Long id) {
        return eventRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The event with id: " + id + " does not exist"));
    }

}