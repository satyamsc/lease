package com.allene.lease.service.impl;

import com.allene.lease.dao.ModelRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.VehicleMapper;
import com.allene.lease.model.Model;
import com.allene.lease.model.Vehicle;
import com.allene.lease.service.VehicleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository repository;
    private final ModelRepository modelRepository;
    private final VehicleMapper mapper;

    /**
     * Saves a vehicle entity.
     *
     * @param dto The vehicle DTO to be saved
     * @return The saved vehicle DTO
     */
    @Override
    public VehicleDTO save(VehicleDTO dto) {
        var entity = mapper.asEntity(dto);
        Model model = getModel(entity);
        entity.setBrand(model.getBrand());
        entity.setModel(model);
        return mapper.asDTO(repository.save(entity));
    }

    /**
     * Retrieves the model entity associated with the vehicle entity.
     *
     * @param entity The vehicle entity
     * @return The associated model entity
     * @throws ResourceNotFoundException If the model entity or brand association is not found
     */
    private Model getModel(Vehicle entity) {
        Model model = modelRepository.findById(entity.getModel().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Model is not found... "));
        if (entity.getBrand().getId() != model.getBrand().getId()) {
            throw new ResourceNotFoundException("Given Model does not belong to the Brand ... ");
        }
        return model;
    }

    /**
     * Deletes a vehicle entity by its ID.
     *
     * @param id The ID of the vehicle entity to be deleted
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            findVehicleById(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request : {}", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to delete the entities with ID: " + id);
        }

    }

    /**
     * Finds a vehicle entity by its ID.
     *
     * @param id The ID of the vehicle entity to be found
     * @return The found vehicle DTO
     * @throws ResourceNotFoundException If the vehicle entity is not found
     */
    @Override
    public VehicleDTO findById(Long id) {
        return mapper.asDTO(findVehicleById(id));
    }

    /**
     * Finds a vehicle entity by its ID.
     *
     * @param id The ID of the vehicle entity to be found
     * @return The found vehicle entity
     * @throws ResourceNotFoundException If the vehicle entity is not found
     */
    private Vehicle findVehicleById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Vehicle is not found... "));
    }

    /**
     * Retrieves all vehicle entities.
     *
     * @return A list of vehicle DTOs
     */
    @Override
    public List<VehicleDTO> findAll() {
        return mapper.asDTOList(repository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    /**
     * Updates a vehicle entity with the provided ID.
     *
     * @param dto The updated vehicle DTO
     * @param id  The ID of the vehicle entity to be updated
     * @return The updated vehicle DTO
     */
    @Override
    public VehicleDTO update(VehicleDTO dto, Long id) {
        try {
            findVehicleById(id);
            dto.setId(id);
            return save(dto);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request : {}",
                    ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to insert the entities with brand ID: " + id);

        }
    }
}