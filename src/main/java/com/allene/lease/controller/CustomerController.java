package com.allene.lease.controller;

import com.allene.lease.dto.CustomerDTO;

import java.util.List;


public interface CustomerController {

    CustomerDTO save(CustomerDTO customer);

    CustomerDTO findById(Long id);

    void delete(Long id);

    List<CustomerDTO> list();

    CustomerDTO update(CustomerDTO dto, Long id);
}