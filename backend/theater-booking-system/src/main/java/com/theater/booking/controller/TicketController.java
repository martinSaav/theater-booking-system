package com.theater.booking.controller;

import com.theater.booking.dto.TicketRequestDTO;
import com.theater.booking.dto.TicketResponseDTO;
import com.theater.booking.exceptions.TicketNotFoundException;
import com.theater.booking.exceptions.NotValidBodyException;
import com.theater.booking.exceptions.UnknownErrorException;
import com.theater.booking.service.TicketService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<TicketResponseDTO>> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getRecordById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new TicketNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<TicketResponseDTO> save(@RequestBody TicketRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable Long id, @RequestBody TicketRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new TicketNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        } catch (EntityNotFoundException e) {
            throw new TicketNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
