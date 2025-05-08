package com.theater.booking.service;

import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.interfaces.IBookingService;
import com.theater.booking.model.*;
import com.theater.booking.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class BookingService implements IBookingService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final AttendanceRepository attendanceRepository;
    private final BookingRepository bookingRepository;

    public BookingService(TicketRepository ticketRepository, CustomerRepository customerRepository, AttendanceRepository attendanceRepository,BookingRepository bookingRepository) {
        this.ticketRepository = ticketRepository;
        this.customerRepository = customerRepository;
        this.attendanceRepository = attendanceRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<BookingResponseDTO> findAll() {
        return bookingRepository.findAll().stream()
                .map(BookingResponseDTO::new)
                .toList();
    }

    @Override
    public BookingResponseDTO findById(Long id) {
        Booking booking = findByIdAux(id);
        return new BookingResponseDTO(booking);
    }

    @Override
    public BookingResponseDTO save(BookingRequestDTO dto) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(dto.getTicketId());
        if (ticketOptional.isEmpty()) {
            throw new EntityNotFoundException("Ticket not found");
        }
        Ticket ticket = ticketOptional.get();
        Event event = ticket.getEvent();

        Customer customer = new Customer();
        customer.setName(dto.getCustomerName());
        customer.setPhone(dto.getPhone());
        Customer customerSaved = customerRepository.save(customer);

        Attendance attendance = new Attendance();
        attendance.setCustomer(customerSaved);
        attendance.setEvent(event);
        Attendance attendanceSaved = attendanceRepository.save(attendance);

        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setCustomer(customerSaved);
        booking.setTicket(ticket);
        Booking bookingSaved = bookingRepository.save(booking);

        return new BookingResponseDTO(bookingSaved);
    }

    @Override
    public BookingResponseDTO update(Long id, BookingRequestDTO dto) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        Booking booking = findByIdAux(id);
        bookingRepository.delete(booking);
        return true;
    }

    Booking findByIdAux(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }
}
