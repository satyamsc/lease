package com.allene.lease.mapper;

import com.allene.lease.dto.ContractDTO;
import com.allene.lease.model.Contract;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContractMapper extends GenericMapper<Contract, ContractDTO> {
    @Override
    Contract asEntity(ContractDTO dto);
}