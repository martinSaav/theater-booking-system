package com.theater.booking.interfaces;


import com.theater.booking.dto.EventResponseDTO;

import java.util.List;

public interface IEventService {

    List<EventResponseDTO> findAll();

    List<EventResponseDTO> findAllAvailable();

    EventResponseDTO findById(Long id);

    Void delete(Long id);
}