package com.theater.booking.interfaces;

import com.theater.booking.dto.request.CustomerRequestDTO;
import com.theater.booking.dto.response.CustomerResponseDTO;

import java.util.List;

public interface ICustomerService {

    List<CustomerResponseDTO> findAll();

    CustomerResponseDTO findById(String id);

    CustomerResponseDTO update(String id, CustomerRequestDTO dto);

    boolean delete(String id);
}
