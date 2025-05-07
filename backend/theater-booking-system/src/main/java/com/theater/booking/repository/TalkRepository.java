package com.theater.booking.repository;


import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Talk;
import org.springframework.stereotype.Repository;

@Repository
public interface TalkRepository extends IBaseRepository<Talk, Long> {
}
