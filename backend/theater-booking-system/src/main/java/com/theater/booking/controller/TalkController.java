package com.theater.booking.controller;

import com.theater.booking.model.Talk;
import com.theater.booking.service.TalkService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/events/talks")
public class TalkController extends BaseController<Talk, TalkService>{


    public TalkController(TalkService service) {
        super(service);
    }

}
