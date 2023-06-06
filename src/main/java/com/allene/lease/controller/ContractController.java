package com.allene.lease.controller;

import com.allene.lease.dto.ContractDTO;
import com.allene.lease.dto.ContractOverviewDTO;

import java.util.List;


public interface ContractController {

    ContractDTO save(ContractDTO contract);


    ContractDTO findByContract(Long id);


    void delete(Long id);

    List<ContractOverviewDTO> overview();


    ContractDTO update(ContractDTO dto, Long id);
}