package com.theater.booking.repository;

import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.TheaterPlay;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterPlayRepository extends IBaseRepository<TheaterPlay, Long> {
}
