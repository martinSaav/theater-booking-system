package com.theater.booking.interfaces;

import com.theater.booking.dto.ConcertRequestDTO;
import com.theater.booking.dto.ConcertResponseDTO;

import java.util.List;

public interface IConcertService {

    List<ConcertResponseDTO> findAll();

    ConcertResponseDTO findById(Long id);

    ConcertResponseDTO save(ConcertRequestDTO dto);

    ConcertResponseDTO update(Long id, ConcertRequestDTO dto);

    boolean delete(Long id);
}
