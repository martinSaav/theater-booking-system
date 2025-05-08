package com.theater.booking.repository;

import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Ticket;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketRepository extends IBaseRepository<Ticket, Long> {
}
