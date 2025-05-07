package com.theater.booking.controller;


import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events")
public class EventController {


    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }
}
