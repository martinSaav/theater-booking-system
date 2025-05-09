package com.theater.booking.service;

import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.interfaces.IBookingService;
import com.theater.booking.model.*;
import com.theater.booking.repository.AttendanceRepository;
import com.theater.booking.repository.BookingRepository;
import com.theater.booking.repository.CustomerRepository;
import com.theater.booking.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingService implements IBookingService {

    private final TicketRepository ticketRepository;
    private final CustomerRepository customerRepository;
    private final AttendanceRepository attendanceRepository;
    private final BookingRepository bookingRepository;

    public BookingService(TicketRepository ticketRepository, CustomerRepository customerRepository, AttendanceRepository attendanceRepository, BookingRepository bookingRepository) {
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

    @Transactional
    @Override
    public BookingResponseDTO save(BookingRequestDTO dto) {
        Ticket ticket = ticketRepository.findById(dto.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        if (ticket.getAvailableStock() <= 0) {
            throw new IllegalStateException("No tickets available");
        }
        ticket.setAvailableStock(ticket.getAvailableStock() - 1);
        ticketRepository.save(ticket);

        Event event = ticket.getEvent();
        Customer customer = customerRepository.findById(dto.getEmail())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setEmail(dto.getEmail());
                    newCustomer.setName(dto.getCustomerName());
                    newCustomer.setPhone(dto.getPhone());
                    return customerRepository.save(newCustomer);
                });

        Attendance attendance = new Attendance();
        attendance.setCustomer(customer);
        attendance.setEvent(event);
        attendanceRepository.save(attendance);

        Booking booking = new Booking();
        booking.setBookingDate(dto.getBookingDate());
        booking.setCustomer(customer);
        booking.setTicket(ticket);
        Booking bookingSaved = bookingRepository.save(booking);

        return new BookingResponseDTO(bookingSaved);
    }

    @Transactional
    @Override
    public BookingResponseDTO update(Long id, BookingRequestDTO dto) {
        return null;
    }

    @Transactional
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
