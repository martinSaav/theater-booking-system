package com.theater.booking.controller;


import com.theater.booking.dto.TheaterPlayRequestDTO;
import com.theater.booking.dto.TheaterPlayResponseDTO;
import com.theater.booking.service.TheaterPlayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events/theater-plays")
public class TheaterPlayController {

    private final TheaterPlayService service;

    public TheaterPlayController(TheaterPlayService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<TheaterPlayResponseDTO>> getAllRecord() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TheaterPlayResponseDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<TheaterPlayResponseDTO> save(@RequestBody TheaterPlayRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping(value = {"", "/{id}"})
    public ResponseEntity<TheaterPlayResponseDTO> update(@PathVariable(required = false) Long id, @RequestBody TheaterPlayRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
