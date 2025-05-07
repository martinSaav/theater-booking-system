package com.theater.booking.service;



import com.theater.booking.interfaces.IEventService;
import com.theater.booking.model.Event;
import com.theater.booking.repository.EventRepository;

import org.springframework.stereotype.Service;


@Service
public class EventService extends BaseService<Event, Long> implements IEventService {

    public EventService(EventRepository eventRepository) {
        super(eventRepository);
    }
}