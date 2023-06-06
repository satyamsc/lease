package com.allene.lease.service.impl;

import com.allene.lease.dao.CustomerRepository;
import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.CustomerMapper;
import com.allene.lease.model.Customer;
import com.allene.lease.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the {@link CustomerService} interface.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    /**
     * Saves a customer entity.
     *
     * @param dto The customer DTO to be saved
     * @return The saved customer DTO
     */
    @Override
    public CustomerDTO save(CustomerDTO dto) {
        return mapper.asDTO(repository.save(mapper.asEntity(dto)));
    }

    /**
     * Deletes a customer entity by its ID.
     *
     * @param id The ID of the customer entity to be deleted
     */
    @Override
    public void deleteById(Long id) {
        try {
            findEntityById(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request for deleting, Data integrity violation   {} ",
                    ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to deleting the entities with Customer ID" + id);
        }
    }

    /**
     * Finds a customer entity by its ID.
     *
     * @param id The ID of the customer entity to be found
     * @return The found customer DTO
     * @throws ResourceNotFoundException If the customer entity is not found
     */

    @Override
    public CustomerDTO findById(Long id) {
        return mapper.asDTO(findEntityById(id));
    }

    /**
     * Retrieves all customer entities.
     *
     * @return A list of customer DTOs
     */
    @Override
    public List<CustomerDTO> findAll() {
        return mapper.asDTOList(repository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    /**
     * Updates a customer entity with the provided ID.
     *
     * @param dto The updated customer DTO
     * @param id  The ID of the customer entity to be updated
     * @return The updated customer DTO
     */
    @Override
    public CustomerDTO update(CustomerDTO dto, Long id) {
        try {
            findEntityById(id);
            dto.setId(id);
            return save(dto);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request for updating  {} ", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to update the entities with Customer ID" + id);
        }

    }

    /**
     * Finds a customer entity by its ID.
     *
     * @param id The ID of the customer entity to be found
     * @return The found customer entity
     * @throws ResourceNotFoundException If the customer entity is not found
     */
    private Customer findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("An error occurred while processing the request for finding with id: {}", id);
                    return new ResourceNotFoundException("Customer is not found with Id" + id);
                });
    }
}
