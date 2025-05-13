package com.theater.booking.interfaces;

import com.theater.booking.dto.request.ConcertRequestDTO;
import com.theater.booking.dto.response.ConcertResponseDTO;

import java.util.List;

public interface IConcertService {

    List<ConcertResponseDTO> findAll();

    ConcertResponseDTO findById(Long id);

    ConcertResponseDTO save(ConcertRequestDTO dto);

    ConcertResponseDTO update(Long id, ConcertRequestDTO dto);
}
