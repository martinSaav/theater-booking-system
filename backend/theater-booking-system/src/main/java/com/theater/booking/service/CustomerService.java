package com.theater.booking.service;

import com.theater.booking.dto.request.CustomerRequestDTO;
import com.theater.booking.dto.response.CustomerResponseDTO;
import com.theater.booking.interfaces.ICustomerService;
import com.theater.booking.model.Customer;
import com.theater.booking.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerResponseDTO::new)
                .toList();
    }

    @Override
    public CustomerResponseDTO findById(String id) {
        Customer customer = findByIdAux(id);
        return new CustomerResponseDTO(customer);
    }

    @Transactional
    @Override
    public CustomerResponseDTO update(String id, CustomerRequestDTO dto) {
        Customer customer = findByIdAux(id);
        customer.setName(dto.getName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());
        return new CustomerResponseDTO(customerRepository.save(customer));
    }

    @Transactional
    @Override
    public boolean delete(String id) {
        Customer customer = findByIdAux(id);
        customerRepository.delete(customer);
        return true;
    }

    private Customer findByIdAux(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("The customer with id: " + id + " does not exist"));
    }
}
