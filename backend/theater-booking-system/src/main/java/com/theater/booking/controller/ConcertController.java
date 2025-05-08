package com.theater.booking.controller;


import com.theater.booking.dto.ConcertRequestDTO;
import com.theater.booking.dto.ConcertResponseDTO;
import com.theater.booking.service.ConcertService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events/concerts")
public class ConcertController {

    private final ConcertService service;

    public ConcertController(ConcertService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<ConcertResponseDTO>> getAllRecord() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertResponseDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<ConcertResponseDTO> save(@RequestBody ConcertRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping(value = {"", "/{id}"})
    public ResponseEntity<ConcertResponseDTO> update(@PathVariable(required = false) Long id, @RequestBody ConcertRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
