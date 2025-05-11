package com.theater.booking.interfaces;

import com.theater.booking.dto.TalkRequestDTO;
import com.theater.booking.dto.TalkResponseDTO;

import java.util.List;

public interface ITalkService {

    List<TalkResponseDTO> findAll();

    TalkResponseDTO findById(Long id);

    TalkResponseDTO save(TalkRequestDTO dto);

    TalkResponseDTO update(Long id, TalkRequestDTO dto);
}