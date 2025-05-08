package com.theater.booking.interfaces;



import com.theater.booking.dto.EventRequestDTO;
import com.theater.booking.dto.EventResponseDTO;

import java.util.List;

public interface IEventService {

    List<EventResponseDTO> findAll();

    EventResponseDTO findById(Long id);

    EventResponseDTO save(EventRequestDTO dto);

    EventResponseDTO update(Long id, EventRequestDTO dto);

    boolean delete(Long id);
}