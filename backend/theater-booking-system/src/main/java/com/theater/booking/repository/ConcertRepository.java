package com.theater.booking.repository;

import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Concert;
import org.springframework.stereotype.Repository;


@Repository
public interface ConcertRepository extends IBaseRepository<Concert, Long> {

}
