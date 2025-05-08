package com.theater.booking.service;

import com.theater.booking.dto.TalkRequestDTO;
import com.theater.booking.dto.TalkResponseDTO;
import com.theater.booking.dto.TicketRequestDTO;
import com.theater.booking.dto.TicketResponseDTO;
import com.theater.booking.interfaces.ITicketService;
import com.theater.booking.model.Talk;
import com.theater.booking.model.Ticket;
import com.theater.booking.repository.EventRepository;
import com.theater.booking.repository.TalkRepository;
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
        ticket.setType(dto.getType());
        ticket.setPrice(dto.getPrice());
        ticket.setTotalStock(dto.getTotalStock());
        ticket.setAvailableStock(dto.getAvailableStock());
        ticket.setEvent(eventRepository.findById(dto.getEventId()).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + dto.getEventId() + " no existe")));
        return new TicketResponseDTO(ticketRepository.save(ticket));
    }

    @Transactional
    @Override
    public TicketResponseDTO update(Long id, TicketRequestDTO dto) {
        Ticket ticket = findByIdAux(id);
        ticket.setType(dto.getType());
        ticket.setPrice(dto.getPrice());
        ticket.setTotalStock(dto.getTotalStock());
        ticket.setAvailableStock(dto.getAvailableStock());
        return new TicketResponseDTO(ticketRepository.save(ticket));
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Ticket findByIdAux(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + id + " no existe"));
    }

}