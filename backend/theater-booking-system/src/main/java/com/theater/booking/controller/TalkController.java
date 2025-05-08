package com.theater.booking.controller;

import com.theater.booking.dto.TalkRequestDTO;
import com.theater.booking.dto.TalkResponseDTO;
import com.theater.booking.service.TalkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events/talks")
public class TalkController {

    private final TalkService service;

    public TalkController(TalkService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<TalkResponseDTO>> getAllRecord() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalkResponseDTO> getRecordById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @PostMapping("")
    public ResponseEntity<TalkResponseDTO> save(@RequestBody TalkRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

    @PutMapping(value = {"", "/{id}"})
    public ResponseEntity<TalkResponseDTO> update(@PathVariable(required = false) Long id, @RequestBody TalkRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
