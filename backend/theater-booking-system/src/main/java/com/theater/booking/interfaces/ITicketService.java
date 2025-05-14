package com.theater.booking.interfaces;

import com.theater.booking.dto.request.TicketRequestDTO;
import com.theater.booking.dto.response.TicketResponseDTO;

import java.util.List;

public interface ITicketService {

    List<TicketResponseDTO> findAll();

    TicketResponseDTO findById(Long id);

    TicketResponseDTO save(TicketRequestDTO dto);

    TicketResponseDTO update(Long id, TicketRequestDTO dto);

    boolean delete(Long id);
}
