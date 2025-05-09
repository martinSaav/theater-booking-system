package com.theater.booking.controller;


import com.theater.booking.dto.CustomerRequestDTO;
import com.theater.booking.dto.CustomerResponseDTO;
import com.theater.booking.exceptions.NotValidBodyException;
import com.theater.booking.exceptions.CustomerNotFoundException;
import com.theater.booking.exceptions.UnknownErrorException;
import com.theater.booking.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getRecordById(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new CustomerNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{email}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable String email, @RequestBody CustomerRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(email, dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new CustomerNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        } catch (EntityNotFoundException e) {
            throw new CustomerNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
