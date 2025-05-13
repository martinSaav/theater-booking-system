package com.theater.booking.controller;


import com.theater.booking.dto.response.EventResponseDTO;
import com.theater.booking.exceptions.EventDeletionNotAllowedException;
import com.theater.booking.exceptions.EventNotFoundException;
import com.theater.booking.exceptions.UnknownErrorException;
import com.theater.booking.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("")
    @Operation(
            description = "Trae todos los eventos",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<List<EventResponseDTO>> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/available")
    @Operation(
            description = "Trae todos los eventos disponibles",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<List<EventResponseDTO>> getAllRecordAvailable() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAllAvailable());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Trae un evento por id",
            parameters = {
                    @Parameter(name = "id", description = "Id del evento", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<EventResponseDTO> getRecordById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new EventNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Elimina un evento por id",
            parameters = {
                    @Parameter(name = "id", description = "Id del evento", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(id));
        } catch (EntityNotFoundException e) {
            throw new EventNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            throw new EventDeletionNotAllowedException(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
