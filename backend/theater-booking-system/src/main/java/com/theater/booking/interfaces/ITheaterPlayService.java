package com.theater.booking.interfaces;

import com.theater.booking.dto.request.TheaterPlayRequestDTO;
import com.theater.booking.dto.response.TheaterPlayResponseDTO;

import java.util.List;

public interface ITheaterPlayService {
    List<TheaterPlayResponseDTO> findAll();

    TheaterPlayResponseDTO findById(Long id);

    TheaterPlayResponseDTO save(TheaterPlayRequestDTO dto);

    TheaterPlayResponseDTO update(Long id, TheaterPlayRequestDTO dto);
}
