package com.theater.booking.service;

import com.theater.booking.interfaces.ITalkService;
import com.theater.booking.model.Talk;
import com.theater.booking.repository.TalkRepository;
import org.springframework.stereotype.Service;

@Service
public class TalkService extends BaseService<Talk, Long> implements ITalkService {

    public TalkService(TalkRepository talkRepository) {
        super(talkRepository);
    }
}