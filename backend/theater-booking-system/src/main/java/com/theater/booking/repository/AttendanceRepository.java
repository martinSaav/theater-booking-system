package com.theater.booking.repository;

import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Attendance;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends IBaseRepository<Attendance, Long> {

}
