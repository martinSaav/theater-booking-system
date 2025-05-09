package com.theater.booking.service;

import com.theater.booking.dto.ConcertRequestDTO;
import com.theater.booking.dto.ConcertResponseDTO;
import com.theater.booking.interfaces.IConcertService;
import com.theater.booking.model.Concert;
import com.theater.booking.repository.ConcertRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService implements IConcertService {

    private final ConcertRepository concertRepository;

    public ConcertService(ConcertRepository concertRepository) {
        this.concertRepository = concertRepository;
    }

    @Override
    public List<ConcertResponseDTO> findAll() {
        return concertRepository.findAll().stream()
                .map(ConcertResponseDTO::new)
                .toList();
    }

    @Override
    public ConcertResponseDTO findById(Long id) {
        Concert concert = findByIdAux(id);
        return new ConcertResponseDTO(concert);
    }

    @Transactional
    @Override
    public ConcertResponseDTO save(ConcertRequestDTO dto) {
        Concert concert = new Concert();
        concert.setName(dto.getName());
        concert.setDateTime(dto.getDateTime());
        concert.setDescription(dto.getDescription());
        return new ConcertResponseDTO(concertRepository.save(concert));
    }

    @Transactional
    @Override
    public ConcertResponseDTO update(Long id, ConcertRequestDTO dto) {
        Concert concert = findByIdAux(id);
        concert.setName(dto.getName());
        concert.setDateTime(dto.getDateTime());
        concert.setDescription(dto.getDescription());
        return new ConcertResponseDTO(concertRepository.save(concert));
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Concert concert = findByIdAux(id);
        concertRepository.delete(concert);
        return true;
    }

    private Concert findByIdAux(Long id) {
        return concertRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + id + " no existe"));
    }
}