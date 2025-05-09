package com.theater.booking.controller;


import com.theater.booking.dto.EventResponseDTO;
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
            description = "Trae todos los eventoss",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200", ref = "ok"),
                    @ApiResponse(responseCode = "500", ref = "internalServerError")
            }
    )
    public ResponseEntity<List<EventResponseDTO>> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Trae un eventos por id",
            parameters = {
                    @Parameter(name = "id", description = "Id del eventos", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", ref = "ok"),
                    @ApiResponse(responseCode = "404", ref = "notFound"),
                    @ApiResponse(responseCode = "500", ref = "internalError")
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
            description = "Elimina un eventos por id",
            parameters = {
                    @Parameter(name = "id", description = "Id del eventos", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "204", ref = "noContent"),
                    @ApiResponse(responseCode = "404", ref = "notFound"),
                    @ApiResponse(responseCode = "500", ref = "internalError")
            }
    )
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
