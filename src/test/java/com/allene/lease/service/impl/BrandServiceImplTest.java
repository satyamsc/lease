package com.allene.lease.service.impl;

import com.allene.lease.dao.BrandRepository;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.BrandMapper;
import com.allene.lease.model.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {BrandServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BrandServiceImplTest {
    @MockBean
    private BrandMapper brandMapper;

    @MockBean
    private BrandRepository brandRepository;

    @Autowired
    private BrandServiceImpl brandServiceImpl;

    @Test
    void shouldSave() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        when(brandRepository.save((Brand) any())).thenReturn(brand);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        when(brandMapper.asEntity((BrandDTO) any())).thenReturn(brand1);
        when(brandMapper.asDTO((Brand) any())).thenReturn(brandDTO);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");
        assertSame(brandDTO, brandServiceImpl.save(brandDTO1));
        verify(brandRepository).save((Brand) any());
        verify(brandMapper).asEntity((BrandDTO) any());
        verify(brandMapper).asDTO((Brand) any());
    }


    @Test
    void shouldNotSaveErrorOccurs() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        when(brandRepository.save((Brand) any())).thenReturn(brand);
        when(brandMapper.asEntity((BrandDTO) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(brandMapper.asDTO((Brand) any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        assertThrows(ResourceNotFoundException.class, () -> brandServiceImpl.save(brandDTO));
        verify(brandMapper).asEntity((BrandDTO) any());
    }


    @Test
    void shouldDeleteById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        doNothing().when(brandRepository).deleteById( any());
        when(brandRepository.findById( any())).thenReturn(ofResult);
        brandServiceImpl.deleteById(1L);
        verify(brandRepository).findById(any());
        verify(brandRepository).deleteById( any());
    }


    @Test
    void shouldNotDeleteByIdWhenErrorOccurs() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        doThrow(new DataIntegrityViolationException("Msg")).when(brandRepository).deleteById(any());
        when(brandRepository.findById(any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> brandServiceImpl.deleteById(1L));
        verify(brandRepository).findById(any());
        verify(brandRepository).deleteById(any());
    }


    @Test
    void shouldNotDeleteByIdWhenErrorThrows() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        doThrow(new AlleneDataIntegrityViolationException("An error occurred")).when(brandRepository)
                .deleteById(any());
        when(brandRepository.findById(any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> brandServiceImpl.deleteById(1L));
        verify(brandRepository).findById( any());
        verify(brandRepository).deleteById( any());
    }

    @Test
    void shouldNotDeleteById() {
        doNothing().when(brandRepository).deleteById( any());
        when(brandRepository.findById( any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> brandServiceImpl.deleteById(1L));
        verify(brandRepository).findById(any());
    }

    @Test
    void shouldFindById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        when(brandMapper.asDTO(any())).thenReturn(brandDTO);
        assertSame(brandDTO, brandServiceImpl.findById(1L));
        verify(brandRepository).findById((Long) any());
        verify(brandMapper).asDTO(any());
    }

    @Test
    void shouldNotFindById1() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);
        when(brandMapper.asDTO((Brand) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> brandServiceImpl.findById(1L));
        verify(brandRepository).findById((Long) any());
        verify(brandMapper).asDTO((Brand) any());
    }


    @Test
    void shouldNotFindById2() {
        when(brandRepository.findById((Long) any())).thenReturn(Optional.empty());

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        when(brandMapper.asDTO((Brand) any())).thenReturn(brandDTO);
        assertThrows(ResourceNotFoundException.class, () -> brandServiceImpl.findById(1L));
        verify(brandRepository).findById((Long) any());
    }


    @Test
    void shouldFindAll() {
        when(brandRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        ArrayList<BrandDTO> brandDTOList = new ArrayList<>();
        when(brandMapper.asDTOList(any())).thenReturn(brandDTOList);
        List<BrandDTO> actualFindAllResult = brandServiceImpl.findAll();
        assertSame(brandDTOList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(brandRepository).findAll((Sort) any());
        verify(brandMapper).asDTOList(any());
    }

    @Test
    void shouldNotFindAll2() {
        when(brandRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        when(brandMapper.asDTOList(any())).thenThrow(new DataIntegrityViolationException("id"));
        assertThrows(DataIntegrityViolationException.class, () -> brandServiceImpl.findAll());
        verify(brandRepository).findAll((Sort) any());
        verify(brandMapper).asDTOList(any());
    }

    @Test
    void shouldUpdate() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        when(brandRepository.save((Brand) any())).thenReturn(brand1);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        when(brandMapper.asDTO((Brand) any())).thenReturn(brandDTO);
        when(brandMapper.asEntity((BrandDTO) any())).thenReturn(brand2);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");
        assertSame(brandDTO, brandServiceImpl.update(brandDTO1, 1L));
        verify(brandRepository).save((Brand) any());
        verify(brandRepository).findById((Long) any());
        verify(brandMapper).asEntity((BrandDTO) any());
        verify(brandMapper).asDTO((Brand) any());
    }


    @Test
    void shouldNotUpdate2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        when(brandRepository.save((Brand) any())).thenReturn(brand1);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");
        when(brandMapper.asDTO(any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(brandMapper.asEntity(any())).thenReturn(brand2);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        assertThrows(DataIntegrityViolationException.class, () -> brandServiceImpl.update(brandDTO, 1L));
        verify(brandRepository).save(any());
        verify(brandRepository).findById( any());
        verify(brandMapper).asEntity( any());
        verify(brandMapper).asDTO( any());
    }

    @Test
    void shouldNotUpdate3() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        when(brandRepository.save( any())).thenReturn(brand);
        when(brandRepository.findById(any())).thenReturn(Optional.empty());

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        when(brandMapper.asDTO(any())).thenReturn(brandDTO);
        when(brandMapper.asEntity( any())).thenReturn(brand1);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");
        assertThrows(ResourceNotFoundException.class, () -> brandServiceImpl.update(brandDTO1, 1L));
        verify(brandRepository).findById( any());
    }
}

