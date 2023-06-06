package com.allene.lease.service.impl;

import com.allene.lease.dao.ContractRepository;
import com.allene.lease.dao.CustomerRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.ContractDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.ContractMapper;
import com.allene.lease.model.Contract;
import com.allene.lease.model.Customer;
import com.allene.lease.model.Vehicle;
import com.allene.lease.service.ContractService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ContractServiceImpl implements ContractService {
    public static final String RESOURCE_IS_NOT_FOUND = "Resource is not found.";
    private final ContractRepository repository;
    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;
    private final ContractMapper mapper;

    /**
     * Saves a contract entity.
     *
     * @param dto The contract DTO to be saved
     * @return The saved contract DTO
     */
    @Override
    public ContractDTO save(ContractDTO dto) {
        Contract entity = mapper.asEntity(dto);
        Vehicle vehicle = findVehicleById(entity);
        Customer customer = findCustomerById(entity);
        entity.setCustomer(customer);
        entity.setVehicle(vehicle);
        return mapper.asDTO(repository.save(entity));
    }

    /**
     * Deletes a contract entity by its contract number.
     *
     * @param num The contract number of the contract entity to be deleted
     */
    @Override
    @Transactional
    public void deleteById(Long num) {
        try {
            findContractByNum(num);
            repository.deleteByContractNumber(num);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request for deletion : {}", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to delete the entity with contract number: " + num);
        }

    }

    /**
     * Finds a contract entity by its contract number.
     *
     * @param id The contract number of the contract entity to be found
     * @return The found contract DTO
     */
    @Override
    public ContractDTO findById(Long id) {
        return mapper.asDTO(findContractByNum(id));
    }

    private Contract findContractByNum(Long num) {
        return repository.findByContractNumber(num)
                .orElseThrow(() -> {
                    log.error("An error occurred while processing the request for finding contract with : {}", num);
                    return new ResourceNotFoundException("Contract is not found with num:" + num);
                });
    }

    /**
     * Retrieves all contract entities.
     *
     * @return A list of contract DTOs
     */
    @Override
    public List<ContractDTO> findAll() {
        return mapper.asDTOList(repository.findAll());
    }

    /**
     * Updates a contract entity with the provided contract number.
     *
     * @param dto The updated contract DTO
     * @param num The contract number of the contract entity to be updated
     * @return The updated contract DTO
     */
    @Override
    public ContractDTO update(ContractDTO dto, Long num) {
        try {
            findContractByNum(num);
            dto.setContractNumber(num);
            return save(dto);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request for update contract: {}",
                    ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to update the entities with contract number: " + num);
        }

    }

    /**
     * Finds a contract entity by its contract number.
     *
     * @param num The contract number of the contract entity to be found
     * @return The found contract entity
     * @throws ResourceNotFoundException If the contract entity is not found
     */
    @Override
    public ContractDTO findByContractNum(Long num) {
        return mapper.asDTO(findContractByNum(num));
    }

    /**
     * Finds a vehicle entity by its ID.
     *
     * @param entity The contract entity
     * @return The found vehicle entity
     * @throws ResourceNotFoundException If the vehicle entity is not found
     */
    private Vehicle findVehicleById(Contract entity) {
        if (Objects.nonNull(entity.getVehicle()) && Objects.nonNull(entity.getVehicle().getId())) {
            long id = entity.getVehicle().getId();
            return vehicleRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Vehicle is not found with id: " + id));
        }
        throw new ResourceNotFoundException(RESOURCE_IS_NOT_FOUND);
    }

    /**
     * Finds a customer entity by its ID.
     *
     * @param entity The contract entity
     * @return The found customer entity
     * @throws ResourceNotFoundException If the customer entity is not found
     */
    private Customer findCustomerById(Contract entity) {
        if (Objects.nonNull(entity.getCustomer()) && Objects.nonNull(entity.getCustomer().getId())) {
            long id = entity.getCustomer().getId();
            return customerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Customer is not found with id " + id));
        }
        throw new ResourceNotFoundException(RESOURCE_IS_NOT_FOUND);
    }
}