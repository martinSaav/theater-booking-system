package com.theater.booking.service;

import com.theater.booking.dto.TalkRequestDTO;
import com.theater.booking.dto.TalkResponseDTO;
import com.theater.booking.interfaces.ITalkService;
import com.theater.booking.model.Talk;
import com.theater.booking.repository.TalkRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TalkService implements ITalkService {

    private final TalkRepository talkRepository;

    public TalkService(TalkRepository talkRepository) {
        this.talkRepository = talkRepository;
    }


    @Override
    public List<TalkResponseDTO> findAll() {
        return talkRepository.findAll().stream()
                .map(TalkResponseDTO::new)
                .toList();
    }

    @Override
    public TalkResponseDTO findById(Long id) {
        Talk talk = findByIdAux(id);
        return new TalkResponseDTO(talk);
    }

    @Transactional
    @Override
    public TalkResponseDTO save(TalkRequestDTO dto) {
        Talk talk = new Talk();
        talk.setName(dto.getName());
        talk.setDateTime(dto.getDateTime());
        talk.setDescription(dto.getDescription());
        return new TalkResponseDTO(talkRepository.save(talk));
    }

    @Transactional
    @Override
    public TalkResponseDTO update(Long id, TalkRequestDTO dto) {
        Talk talk = findByIdAux(id);
        talk.setName(dto.getName());
        talk.setDateTime(dto.getDateTime());
        talk.setDescription(dto.getDescription());
        return new TalkResponseDTO(talkRepository.save(talk));
    }

    @Override
    public boolean delete(Long id) {
        Talk talk = findByIdAux(id);
        talkRepository.delete(talk);
        return true;
    }

    private Talk findByIdAux(Long id) {
        return talkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + id + " no existe"));
    }
}