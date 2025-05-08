package com.theater.booking.interfaces;


import com.theater.booking.dto.EventResponseDTO;

import java.util.List;

public interface IEventService {

    List<EventResponseDTO> findAll();

    EventResponseDTO findById(Long id);

    boolean delete(Long id);
}