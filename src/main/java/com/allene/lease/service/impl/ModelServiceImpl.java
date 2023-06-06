package com.allene.lease.service.impl;

import com.allene.lease.dao.BrandRepository;
import com.allene.lease.dao.ModelRepository;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.dto.ModelDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.ModelMapper;
import com.allene.lease.model.Brand;
import com.allene.lease.model.Model;
import com.allene.lease.service.ModelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link ModelService} interface.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ModelServiceImpl implements ModelService {
    private final ModelRepository repository;
    private final BrandRepository brandRepository;
    private final ModelMapper mapper;

    /**
     * Saves a model entity with the provided brand ID.
     *
     * @param dto The model DTO to be saved
     * @param id  The ID of the brand associated with the model
     * @return The saved model DTO
     */
    @Override
    public ModelDTO save(ModelDTO dto, Long id) {
        try {
            BrandDTO brandDTO = new BrandDTO();
            brandDTO.setId(id);
            dto.setBrandDTO(brandDTO);
            return save(dto);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request for updating  {} ", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to update the entities with ID: " + id);
        }
    }

    /**
     * Saves model entities with the provided brand ID.
     *
     * @param dtoList The model DTO to be saved
     * @param id      The ID of the brand associated with the model
     * @return The saved entities and list of model DTO
     */
    @Override
    public List<ModelDTO> saveAll(List<ModelDTO> dtoList, Long id) {
        Brand brand = getBrandEntity(id);
        try {
            var models = mapper.asEntityList(dtoList);
            var entities = models.stream().map(m -> getModel(brand, m)).collect(Collectors.toList());
            return mapper.asDTOList(repository.saveAll(entities));
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request : {}", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to insert the entities with brand ID: " + id);
        }
    }

    private static Model getModel(Brand brand, Model model) {
        model.setBrand(brand);
        return model;
    }

    /**
     * Retrieves the brand entity with the provided ID.
     *
     * @param id The ID of the brand entity to be retrieved
     * @return The brand entity
     * @throws ResourceNotFoundException If the brand entity is not found
     */
    private Brand getBrandEntity(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Brand is not found with Id: {}", id);
                    return new ResourceNotFoundException("Brand is not found with Id: " + id);
                });
    }

    /**
     * Saves a model entity.
     *
     * @param dto The model DTO to be saved
     * @return The saved model DTO
     */
    @Override
    public ModelDTO save(ModelDTO dto) {
        Brand brand = getBrandEntity(dto.getBrandDTO().getId());
        Model model = mapper.asEntity(dto);
        model.setBrand(brand);
        return mapper.asDTO(repository.save(model));
    }

    /**
     * Deletes a model entity by its ID.
     *
     * @param id The ID of the model entity to be deleted
     */
    @Override
    public void deleteById(Long id) {
        findEntityById(id);
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            log.error("An error occurred while processing the request : {}", e.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to delete the entity with ID: " + id);
        }
    }

    /**
     * Finds a model entity by its ID.
     *
     * @param id The ID of the model entity to be found
     * @return The found model DTO
     * @throws ResourceNotFoundException If the model entity is not found
     */
    @Override
    public ModelDTO findById(Long id) {
        return mapper.asDTO(findEntityById(id));
    }

    /**
     * Finds a model entity by its ID.
     *
     * @param id The ID of the model entity to be found
     * @return The found model entity
     * @throws ResourceNotFoundException If the model entity is not found
     */
    private Model findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Model is not found with id {}", id);
                    return new ResourceNotFoundException("Model is not found with id " + id);
                });
    }

    /**
     * Retrieves all model entities.
     *
     * @return A list of model DTOs
     */

    @Override
    public List<ModelDTO> findAll() {
        return mapper.asDTOList(repository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    /**
     * Updates a model entity with the provided ID.
     *
     * @param dto The updated model DTO
     * @param id  The ID of the model entity to be updated
     * @return The updated model DTO
     */
    @Override
    public ModelDTO update(ModelDTO dto, Long id) {
        try {
            dto.setId(id);
            var model = mapper.asEntity(dto);
            model.setBrand(findEntityById(id).getBrand());
            return mapper.asDTO(repository.save(model));
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request for updating  {} ", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to update the entities with ID" + id);
        }

    }

    /**
     * Finds model entities by the brand ID.
     *
     * @param id The ID of the brand associated with the models
     * @return A list of model DTOs
     */
    @Override
    public List<ModelDTO> findAllByBrandId(Long id) {
        return mapper.asDTOList(repository.findALlByBrandId(id));
    }
}