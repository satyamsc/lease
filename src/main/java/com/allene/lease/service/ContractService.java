package com.allene.lease.service;

import com.allene.lease.dto.ContractDTO;

public interface ContractService extends GenericService<ContractDTO, Long> {
    ContractDTO findByContractNum(Long num);
}