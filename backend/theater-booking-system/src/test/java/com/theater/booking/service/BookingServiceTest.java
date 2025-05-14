package com.theater.booking.service;

import com.theater.booking.dto.request.BookingRequestDTO;
import com.theater.booking.dto.response.BookingResponseDTO;
import com.theater.booking.exceptions.BookingDeletionNotAllowedException;
import com.theater.booking.exceptions.BookingNotFoundException;
import com.theater.booking.exceptions.DuplicateAttendanceException;
import com.theater.booking.exceptions.NoTicketsAvailableException;
import com.theater.booking.model.*;
import com.theater.booking.repository.AttendanceRepository;
import com.theater.booking.repository.BookingRepository;
import com.theater.booking.repository.CustomerRepository;
import com.theater.booking.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookingServiceTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllReturnsMappedDTOs() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBookingDate(LocalDateTime.now());
        booking.setCustomer(new Customer("email", "Name", "123", new ArrayList<>(), new ArrayList<>(), 0, 0));
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setType("VIP");
        ticket.setPrice(100.0);
        ticket.setAvailableStock(10);
        ticket.setTotalStock(10);
        Event event = new Event();
        event.setId(1L);
        event.setName("Concert");
        event.setDateTime(LocalDateTime.now());
        event.setDescription("A great concert");
        ticket.setEvent(event);
        booking.setTicket(ticket);
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<BookingResponseDTO> result = bookingService.findAll();
        assertEquals(1, result.size());
        verify(bookingRepository).findAll();
    }

    @Test
    void testFindByIdReturnsDTO() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setBookingDate(LocalDateTime.now());
        booking.setCustomer(new Customer("email", "Name", "123", new ArrayList<>(), new ArrayList<>(), 0, 0));
        Ticket ticket = new Ticket();
        ticket.setId(1L);
        ticket.setType("VIP");
        ticket.setPrice(100.0);
        ticket.setAvailableStock(10);
        ticket.setTotalStock(10);
        Event event = new Event();
        event.setId(1L);
        event.setName("Concert");
        event.setDateTime(LocalDateTime.now());
        event.setDescription("A great concert");
        ticket.setEvent(event);
        booking.setTicket(ticket);
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        BookingResponseDTO dto = bookingService.findById(1L);
        assertEquals(1L, dto.getId());
        verify(bookingRepository).findById(1L);
    }

    @Test
    void testFindByIdThrowsException() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> bookingService.findById(1L));
    }

    @Test
    void testSaveThrowsIfNoTicketAvailable() {
        Ticket ticket = new Ticket();
        ticket.setAvailableStock(0);
        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));

        BookingRequestDTO dto = new BookingRequestDTO("Name", "email", "123", 1L);
        assertThrows(NoTicketsAvailableException.class, () -> bookingService.save(dto));
    }

    @Test
    void testSaveThrowsIfAlreadyHasAttendance() {
        Ticket ticket = new Ticket();
        ticket.setAvailableStock(5);
        Event event = new Event();
        event.setId(1L);
        event.setDateTime(LocalDateTime.now().plusDays(10));
        ticket.setEvent(event);

        Customer customer = new Customer();
        customer.setEmail("email");
        customer.setAttendances(List.of(new Attendance(customer, event)));

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        BookingRequestDTO dto = new BookingRequestDTO("Name", "email", "123", 1L);
        assertThrows(DuplicateAttendanceException.class, () -> bookingService.save(dto));
    }

    @Test
    void testEarnsFreePassAfterFiveAttendances() {
        Ticket ticket = new Ticket();
        ticket.setAvailableStock(10);
        ticket.setPrice(300.0);
        Event event = new Event();
        event.setId(100L);
        event.setDateTime(LocalDateTime.now().plusDays(10));
        ticket.setEvent(event);

        Customer customer = new Customer();
        customer.setEmail("email@example.com");
        customer.setName("Customer");
        customer.setPhone("123");
        customer.setRewardCount(0); // No rewards yet

        List<Attendance> recentAttendances = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Event pastEvent = new Event();
            pastEvent.setId((long) i);
            pastEvent.setDateTime(LocalDateTime.now().minusMonths(2).plusDays(i)); // within last year

            Attendance attendance = new Attendance();
            attendance.setCustomer(customer);
            attendance.setEvent(pastEvent);
            recentAttendances.add(attendance);
        }

        customer.setAttendances(recentAttendances);

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        BookingRequestDTO dto = new BookingRequestDTO("Customer", "email@example.com", "123", 1L);
        bookingService.save(dto);

        assertEquals(1, customer.getRewardCount());
    }

    @Test
    void testNotEarnsFreePassWithFiveAttendances() {
        Ticket ticket = new Ticket();
        ticket.setAvailableStock(10);
        ticket.setPrice(300.0);
        Event event = new Event();
        event.setId(100L);
        event.setDateTime(LocalDateTime.now().plusDays(10));
        ticket.setEvent(event);

        Customer customer = new Customer();
        customer.setEmail("email@example.com");
        customer.setName("Customer");
        customer.setPhone("123");
        customer.setRewardCount(0);

        List<Attendance> recentAttendances = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Event pastEvent = new Event();
            pastEvent.setId((long) i);
            pastEvent.setDateTime(LocalDateTime.now().minusMonths(2).plusDays(i));

            Attendance attendance = new Attendance();
            attendance.setCustomer(customer);
            attendance.setEvent(pastEvent);
            recentAttendances.add(attendance);
        }

        customer.setAttendances(recentAttendances);

        when(ticketRepository.findById(any())).thenReturn(Optional.of(ticket));
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(bookingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        BookingRequestDTO dto = new BookingRequestDTO("Customer", "email@example.com", "123", 1L);
        bookingService.save(dto);

        assertEquals(0, customer.getRewardCount());
    }


    @Test
    void testDeleteThrowsIfEventTooSoon() {
        Booking booking = new Booking();
        Ticket ticket = new Ticket();
        Event event = new Event();
        event.setDateTime(LocalDateTime.now().plusDays(2));
        ticket.setEvent(event);
        booking.setTicket(ticket);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        assertThrows(BookingDeletionNotAllowedException.class, () -> bookingService.delete(1L));
    }

    @Test
    void testDeleteSuccess() {
        Booking booking = new Booking();
        Ticket ticket = new Ticket();
        ticket.setAvailableStock(5);
        Event event = new Event();
        event.setDateTime(LocalDateTime.now().plusDays(10));
        ticket.setEvent(event);
        booking.setTicket(ticket);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        bookingService.delete(1L);

        assertEquals(6, ticket.getAvailableStock());
        verify(bookingRepository).delete(booking);
    }
}
