package com.allene.lease.mapper;

import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends GenericMapper<Customer, CustomerDTO> {
    @Override
    @Mapping(target = "id")
    Customer asEntity(CustomerDTO dto);
}