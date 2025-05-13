package com.theater.booking.interfaces;

import com.theater.booking.dto.request.BookingRequestDTO;
import com.theater.booking.dto.response.BookingResponseDTO;

import java.util.List;

public interface IBookingService {

    List<BookingResponseDTO> findAll();

    BookingResponseDTO findById(Long id);

    BookingResponseDTO save(BookingRequestDTO dto);

    BookingResponseDTO update(Long id, BookingRequestDTO dto);

    Void delete(Long id);
}
