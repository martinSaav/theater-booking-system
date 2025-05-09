package com.theater.booking.controller;


import com.theater.booking.dto.CustomerRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.dto.CustomerResponseDTO;
import com.theater.booking.service.BookingService;
import com.theater.booking.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<List<CustomerResponseDTO>> getAllRecord() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getRecordById(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }


    @PutMapping(value = {"", "/{id}"})
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable String id, @RequestBody CustomerRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.delete(id));
    }
}
