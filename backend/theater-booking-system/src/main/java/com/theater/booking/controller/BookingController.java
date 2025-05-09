package com.theater.booking.controller;


import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.exceptions.BookingNotFoundException;
import com.theater.booking.exceptions.EventNotFoundException;
import com.theater.booking.exceptions.NotValidBodyException;
import com.theater.booking.exceptions.UnknownErrorException;
import com.theater.booking.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<BookingResponseDTO>> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getRecordById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new BookingNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<BookingResponseDTO> save(@RequestBody BookingRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping( "/{id}")
    public ResponseEntity<BookingResponseDTO> update(@PathVariable(required = false) Long id, @RequestBody BookingRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new BookingNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        } catch (EntityNotFoundException e) {
            throw new EventNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
