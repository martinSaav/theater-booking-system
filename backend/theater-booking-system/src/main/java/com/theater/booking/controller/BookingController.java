package com.theater.booking.controller;


import com.theater.booking.dto.request.BookingRequestDTO;
import com.theater.booking.dto.response.BookingResponseDTO;
import com.theater.booking.exceptions.BusinessException;
import com.theater.booking.exceptions.UnknownErrorException;
import com.theater.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
    @Operation(
            description = "Trae todas las reservas",
            parameters = {},
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<List<BookingResponseDTO>> getAllRecord() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @Operation(
            description = "Trae una reserva por id",
            parameters = {
                    @Parameter(name = "id", description = "Id de la reserva", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<BookingResponseDTO> getRecordById(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    @Operation(
            description = "Crea una reserva",
            parameters = {
                    @Parameter(name = "dto", description = "Un dto con los datos de la nueva reserva", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<BookingResponseDTO> save(@Valid @RequestBody BookingRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Operation(
            description = "Actualiza una reserva por id",
            parameters = {
                    @Parameter(name = "id", description = "Id de la reserva", required = true),
                    @Parameter(name = "dto", description = "Un dto con los datos de la reserva a actualizar", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400"),
                    @ApiResponse(responseCode = "404"),
                    @ApiResponse(responseCode = "409"),
                    @ApiResponse(responseCode = "500")
            }
    )
    public ResponseEntity<BookingResponseDTO> update(@Valid @PathVariable Long id, @RequestBody BookingRequestDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(id, dto));
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            description = "Elimina una reserva por id",
            parameters = {
                    @Parameter(name = "id", description = "Id de la reserva", required = true)
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
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new UnknownErrorException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
