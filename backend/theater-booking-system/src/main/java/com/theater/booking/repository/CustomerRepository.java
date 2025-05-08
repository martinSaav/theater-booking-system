package com.theater.booking.repository;

import com.theater.booking.interfaces.IBaseRepository;
import com.theater.booking.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends IBaseRepository<Customer, Long> {
}
