package com.theater.booking.repository;


import com.theater.booking.interfaces.BaseRepository;
import com.theater.booking.model.Event;

import org.springframework.stereotype.Repository;


@Repository
public interface EventRepository extends BaseRepository<Event, Long> {

}