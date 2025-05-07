package com.theater.booking.controller;



import com.theater.booking.interfaces.IBaseController;
import com.theater.booking.interfaces.IBaseService;
import com.theater.booking.model.Base;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


public abstract class BaseController<E extends Base, S extends IBaseService<E, Long>> implements IBaseController<E, Long> {

    S service;

    public BaseController(S service) {
        this.service = service;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAllRecord() {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
    }

    @Override
    @PutMapping(value = {"", "/{id}"})
    public ResponseEntity<?> update(@PathVariable(required = false) Long id, @RequestBody E entity) {
        if (id == null) {
            id = entity.getId();
        }
        return ResponseEntity.status(HttpStatus.OK).body(service.update(id, entity));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
         return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
    }
}