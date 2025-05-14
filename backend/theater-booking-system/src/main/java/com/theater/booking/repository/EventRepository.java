package com.theater.booking.repository;


import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Event;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EventRepository extends IBaseRepository<Event, Long> {

    List<Event> findAllByOrderByIdAsc();

}