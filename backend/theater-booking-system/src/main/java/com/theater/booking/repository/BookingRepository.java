package com.theater.booking.repository;

import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Booking;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends IBaseRepository<Booking, Long> {

}
