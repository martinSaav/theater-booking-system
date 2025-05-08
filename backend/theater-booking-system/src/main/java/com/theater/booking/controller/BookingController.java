package com.theater.booking.controller;


import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.dto.EventRequestDTO;
import com.theater.booking.dto.EventResponseDTO;
import com.theater.booking.model.Booking;
import com.theater.booking.service.BookingService;
import com.theater.booking.service.EventService;
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
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<BookingResponseDTO> save(@RequestBody BookingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping(value = {"", "/{id}"})
    public ResponseEntity<BookingResponseDTO> update(@PathVariable(required = false) Long id, @RequestBody BookingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
