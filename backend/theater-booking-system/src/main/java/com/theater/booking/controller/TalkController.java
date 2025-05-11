package com.theater.booking.controller;

import com.theater.booking.dto.TalkRequestDTO;
import com.theater.booking.dto.TalkResponseDTO;
import com.theater.booking.exceptions.EventNotFoundException;
import com.theater.booking.exceptions.NotValidBodyException;
import com.theater.booking.exceptions.UnknownErrorException;
import com.theater.booking.service.TalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Operation(
            description = "Trae todas las charlas",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<List<TalkResponseDTO>> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Trae una charla por id",
            parameters = {
                    @Parameter(name = "id", description = "Id de la charla", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<TalkResponseDTO> getRecordById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (EntityNotFoundException e) {
            throw new EventNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @Operation(
            description = "Crea una charla",
            parameters = {
                    @Parameter(name = "dto", description = "Un dto con los datos de la nueva charla", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<TalkResponseDTO> save(@RequestBody TalkRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            description = "Actualiza una charla por id",
            parameters = {
                    @Parameter(name = "id", description = "Id de la charla", required = true),
                    @Parameter(name = "dto", description = "Un dto con los datos de la charla a actualizar", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<TalkResponseDTO> update(@PathVariable Long id, @RequestBody TalkRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
        } catch (DataIntegrityViolationException e) {
            throw new NotValidBodyException(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new EventNotFoundException(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
