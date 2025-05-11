package com.theater.booking.service;


import com.theater.booking.dto.TheaterPlayRequestDTO;
import com.theater.booking.dto.TheaterPlayResponseDTO;
import com.theater.booking.interfaces.ITheaterPlayService;
import com.theater.booking.model.TheaterPlay;
import com.theater.booking.repository.TheaterPlayRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheaterPlayService implements ITheaterPlayService {

    private final TheaterPlayRepository theaterPlayRepository;

    public TheaterPlayService(TheaterPlayRepository theaterPlayRepository) {
        this.theaterPlayRepository = theaterPlayRepository;
    }

    @Override
    public List<TheaterPlayResponseDTO> findAll() {
        return theaterPlayRepository.findAll().stream()
                .map(TheaterPlayResponseDTO::new)
                .toList();
    }

    @Override
    public TheaterPlayResponseDTO findById(Long id) {
        TheaterPlay theaterPlay = findByIdAux(id);
        return new TheaterPlayResponseDTO(theaterPlay);
    }

    @Transactional
    @Override
    public TheaterPlayResponseDTO save(TheaterPlayRequestDTO dto) {
        TheaterPlay theaterPlay = new TheaterPlay();
        theaterPlay.setName(dto.getName());
        theaterPlay.setDateTime(dto.getDateTime());
        theaterPlay.setDescription(dto.getDescription());
        return new TheaterPlayResponseDTO(theaterPlayRepository.save(theaterPlay));
    }

    @Transactional
    @Override
    public TheaterPlayResponseDTO update(Long id, TheaterPlayRequestDTO dto) {
        TheaterPlay theaterPlay = findByIdAux(id);
        theaterPlay.setName(dto.getName());
        theaterPlay.setDateTime(dto.getDateTime());
        theaterPlay.setDescription(dto.getDescription());
        return new TheaterPlayResponseDTO(theaterPlayRepository.save(theaterPlay));
    }


    private TheaterPlay findByIdAux(Long id) {
        return theaterPlayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + id + " no existe"));
    }
}
