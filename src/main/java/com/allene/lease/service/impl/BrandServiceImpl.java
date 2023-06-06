package com.allene.lease.service.impl;

import com.allene.lease.dao.BrandRepository;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.BrandMapper;
import com.allene.lease.model.Brand;
import com.allene.lease.service.BrandService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Implementation of the {@link BrandServiceImpl} interface.
 */
@Service
@AllArgsConstructor
@Slf4j
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repository;
    private final BrandMapper mapper;

    /**
     * Saves a brand entity.
     *
     * @param entity The brand DTO to be saved
     * @return The saved brand DTO
     */
    @Override
    public BrandDTO save(BrandDTO entity) {
        return mapper.asDTO(repository.save(mapper.asEntity(entity)));
    }

    /**
     * Deletes a brand entity by its ID.
     *
     * @param id The ID of the brand entity to be deleted
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            findEntityById(id);
            repository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            log.error("An error occurred while processing the request : {}", ex.getMessage());
            throw new AlleneDataIntegrityViolationException("Failed to delete the entities with brand ID: " + id);
        }

    }

    /**
     * Finds a brand entity by its ID.
     *
     * @param id The ID of the brand entity to be found
     * @return The found brand DTO
     */
    @Override
    public BrandDTO findById(Long id) {
        return mapper.asDTO(findEntityById(id));
    }

    /**
     * Retrieves all brand entities.
     *
     * @return A list of brand DTOs
     */
    @Override
    public List<BrandDTO> findAll() {
        return mapper.asDTOList(repository.findAll(Sort.by(Sort.Direction.ASC, "id")));
    }

    /**
     * Updates a brand entity with the provided ID.
     *
     * @param dto The updated brand DTO
     * @param id  The ID of the brand entity to be updated
     * @return The updated brand DTO
     */
    @Override
    public BrandDTO update(BrandDTO dto, Long id) {
        findEntityById(id);
        Brand entity = mapper.asEntity(dto);
        entity.setId(id);
        return mapper.asDTO(repository.save(entity));
    }

    /**
     * Finds a brand entity by its ID.
     *
     * @param id The ID of the brand entity to be found
     * @return The found brand entity
     * @throws ResourceNotFoundException If the brand entity is not found
     */
    private Brand findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("An error occurred while processing the request : {}", id);
                    return new ResourceNotFoundException("Brand not found with id: " + id);
                });
    }
}