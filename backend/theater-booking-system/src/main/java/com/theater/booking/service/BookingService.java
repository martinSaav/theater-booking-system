package com.theater.booking.service;

import com.theater.booking.dto.BookingRequestDTO;
import com.theater.booking.dto.BookingResponseDTO;
import com.theater.booking.exceptions.DuplicateAttendanceException;
import com.theater.booking.exceptions.NoTicketsAvailableException;
import com.theater.booking.interfaces.IBookingService;
import com.theater.booking.model.*;
import com.theater.booking.repository.AttendanceRepository;
import com.theater.booking.repository.BookingRepository;
import com.theater.booking.repository.CustomerRepository;
import com.theater.booking.repository.TicketRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        if (dto.getCustomerEmail() == null || dto.getCustomerName() == null || dto.getCustomerPhone() == null) {
            throw new IllegalArgumentException("Customer details are required");
        }
        Ticket ticket = ticketRepository.findById(dto.getTicketId()).orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
        if (ticket.getAvailableStock() <= 0) {
            throw new NoTicketsAvailableException("No tickets available");
        }
        ticket.setAvailableStock(ticket.getAvailableStock() - 1);
        ticketRepository.save(ticket);

        Event event = ticket.getEvent();
        Customer customer = customerRepository.findById(dto.getCustomerEmail())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setEmail(dto.getCustomerEmail());
                    newCustomer.setName(dto.getCustomerName());
                    newCustomer.setPhone(dto.getCustomerPhone());
                    return customerRepository.save(newCustomer);
                });

        customer.getAttendances().stream()
                .filter(attendance -> attendance.getEvent().getId().equals(event.getId()))
                .findFirst()
                .ifPresentOrElse(
                        attendance -> {
                            throw new DuplicateAttendanceException("Customer already has an ticket for this event");
                        },
                        () -> {
                            Attendance attendance = new Attendance();
                            attendance.setCustomer(customer);
                            attendance.setEvent(event);
                            attendanceRepository.save(attendance);
                        }
                );

        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        booking.setCustomer(customer);
        booking.setTicket(ticket);
        Booking bookingSaved = bookingRepository.save(booking);

        return new BookingResponseDTO(bookingSaved);
    }

    @Transactional
    @Override
    public BookingResponseDTO update(Long id, BookingRequestDTO dto) {
        Booking booking = findByIdAux(id);
        booking.getTicket().setAvailableStock(booking.getTicket().getAvailableStock() + 1);
        Ticket ticket = ticketRepository.findById(dto.getTicketId()).orElseThrow(() -> new EntityNotFoundException("The ticket with id: " + dto.getTicketId() + " does not exist"));
        if (ticket.getAvailableStock() <= 0) {
            throw new IllegalStateException("No tickets available");
        }
        ticket.setAvailableStock(ticket.getAvailableStock() - 1);
        ticketRepository.save(ticket);

        booking.setTicket(ticket);
        booking.setBookingDate(LocalDateTime.now());
        Booking bookingUpdated = bookingRepository.save(booking);

        return new BookingResponseDTO(bookingUpdated);
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Booking booking = findByIdAux(id);
        booking.getTicket().setAvailableStock(booking.getTicket().getAvailableStock() + 1);
        Event event = booking.getTicket().getEvent();
        if (event.getDateTime().isBefore(LocalDateTime.now().plusDays(5))) {
            throw new IllegalStateException("Cannot cancel booking less than 5 days before the event");
        }
        if (event.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot cancel booking for past events");
        }
        bookingRepository.delete(booking);
        return true;
    }

    Booking findByIdAux(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("The booking with id: " + id + " does not exist"));
    }
}
