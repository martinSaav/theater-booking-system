package com.theater.booking.controller;


import com.theater.booking.model.Event;
import com.theater.booking.service.EventService;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events")
public class EventController extends BaseController<Event, EventService>{


    public EventController(EventService service) {
        super(service);
    }

}
