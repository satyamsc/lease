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

@ContextConfiguration(classes = {ModelServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ModelServiceImplTest {
    @MockBean
    private BrandRepository brandRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private ModelRepository modelRepository;

    @Autowired
    private ModelServiceImpl modelServiceImpl;

    @Test
    void shouldSave() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand1);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO(any())).thenReturn(modelDTO);
        when(modelMapper.asEntity(any())).thenReturn(model1);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO1);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");
        assertSame(modelDTO, modelServiceImpl.save(modelDTO1));
        verify(modelRepository).save(any());
        verify(brandRepository).findById(any());
        verify(modelMapper).asEntity(any());
        verify(modelMapper).asDTO(any());
    }

    @Test
    void shouldNotSave2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand1);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(modelMapper.asEntity((ModelDTO) any())).thenReturn(model1);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        assertThrows(DataIntegrityViolationException.class, () -> modelServiceImpl.save(modelDTO));
        verify(modelRepository).save((Model) any());
        verify(brandRepository).findById((Long) any());
        verify(modelMapper).asEntity((ModelDTO) any());
        verify(modelMapper).asDTO((Model) any());
    }

    @Test
    void shouldNotSave3() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model);
        when(brandRepository.findById((Long) any())).thenReturn(Optional.empty());

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand1);
        model1.setId(1L);
        model1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenReturn(modelDTO);
        when(modelMapper.asEntity((ModelDTO) any())).thenReturn(model1);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO1);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");
        assertThrows(ResourceNotFoundException.class, () -> modelServiceImpl.save(modelDTO1));
        verify(brandRepository).findById((Long) any());
    }

    @Test
    void shouldSave4() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand1);
        when(brandRepository.findById(any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO(any())).thenReturn(modelDTO);
        when(modelMapper.asEntity(any())).thenReturn(model1);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO1);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");
        assertSame(modelDTO, modelServiceImpl.save(modelDTO1, 1L));
        verify(modelRepository).save( any());
        verify(brandRepository).findById( any());
        verify(modelMapper).asEntity(any());
        verify(modelMapper).asDTO(any());
        assertEquals(1L, modelDTO1.getBrandDTO().getId().longValue());
    }

    @Test
    void shouldNotSave5() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save(any())).thenReturn(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand1);
        when(brandRepository.findById(any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");
        when(modelMapper.asDTO(any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(modelMapper.asEntity(any())).thenReturn(model1);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        assertThrows(AlleneDataIntegrityViolationException.class, () -> modelServiceImpl.save(modelDTO, 1L));
        verify(modelRepository).save(any());
        verify(brandRepository).findById(any());
        verify(modelMapper).asEntity(any());
        verify(modelMapper).asDTO(any());
    }


    @Test
    void shouldNotSave6() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save( any())).thenReturn(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand1);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");
        when(modelMapper.asDTO( any())).thenThrow(new AlleneDataIntegrityViolationException("An error occurred"));
        when(modelMapper.asEntity( any())).thenReturn(model1);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        assertThrows(AlleneDataIntegrityViolationException.class, () -> modelServiceImpl.save(modelDTO, 1L));
        verify(modelRepository).save((Model) any());
        verify(brandRepository).findById((Long) any());
        verify(modelMapper).asEntity((ModelDTO) any());
        verify(modelMapper).asDTO((Model) any());
    }

    @Test
    void shouldNotSave7() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model);
        when(brandRepository.findById((Long) any())).thenReturn(Optional.empty());

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand1);
        model1.setId(1L);
        model1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenReturn(modelDTO);
        when(modelMapper.asEntity((ModelDTO) any())).thenReturn(model1);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO1);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");
        assertThrows(ResourceNotFoundException.class, () -> modelServiceImpl.save(modelDTO1, 1L));
        verify(brandRepository).findById((Long) any());
    }

    @Test
    void shouldSaveAll() {
        when(modelRepository.saveAll(any())).thenReturn(new ArrayList<>());

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);
        ArrayList<ModelDTO> modelDTOList = new ArrayList<>();
        when(modelMapper.asDTOList(any())).thenReturn(modelDTOList);
        when(modelMapper.asEntityList(any())).thenReturn(new ArrayList<>());
        List<ModelDTO> actualSaveAllResult = modelServiceImpl.saveAll(new ArrayList<>(), 1L);
        assertSame(modelDTOList, actualSaveAllResult);
        assertTrue(actualSaveAllResult.isEmpty());
        verify(modelRepository).saveAll(any());
        verify(brandRepository).findById((Long) any());
        verify(modelMapper).asDTOList(any());
        verify(modelMapper).asEntityList(any());
    }


    @Test
    void shouldNotSaveAll2() {
        when(modelRepository.saveAll(any())).thenReturn(new ArrayList<>());

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);
        when(modelMapper.asDTOList(any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(modelMapper.asEntityList(any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, this::invokeSaveAllWithEmptyList, "Expected ResourceNotFoundException to be thrown");
        verify(brandRepository).findById((Long) any());
        verify(modelMapper).asEntityList(any());
    }

    private void invokeSaveAllWithEmptyList() {
        modelServiceImpl.saveAll(new ArrayList<>(), 1L);
    }

    @Test
    void shouldNotSaveAll3() {
        when(modelRepository.saveAll(any())).thenReturn(new ArrayList<>());
        when(brandRepository.findById(any())).thenReturn(Optional.empty());
        when(modelMapper.asDTOList(any())).thenReturn(new ArrayList<>());
        when(modelMapper.asEntityList(any())).thenReturn(new ArrayList<>());
        assertThrows(ResourceNotFoundException.class, this::invokeSaveAllWithEmptyList,
                "Expected ResourceNotFoundException to be thrown");
        verify(brandRepository).findById(any());
    }


    @Test
    void shouldSaveAll4() {
        when(modelRepository.saveAll(any())).thenReturn(new ArrayList<>());

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        when(brandRepository.findById(any())).thenReturn(ofResult);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        ArrayList<Model> modelList = new ArrayList<>();
        modelList.add(model);
        ArrayList<ModelDTO> modelDTOList = new ArrayList<>();
        when(modelMapper.asDTOList(any())).thenReturn(modelDTOList);
        when(modelMapper.asEntityList(any())).thenReturn(modelList);
        List<ModelDTO> actualSaveAllResult = modelServiceImpl.saveAll(new ArrayList<>(), 1L);
        assertSame(modelDTOList, actualSaveAllResult);
        assertTrue(actualSaveAllResult.isEmpty());
        verify(modelRepository).saveAll(any());
        verify(brandRepository).findById(any());
        verify(modelMapper).asDTOList(any());
        verify(modelMapper).asEntityList(any());
    }


    @Test
    void shouldSaveAll5() {
        when(modelRepository.saveAll(any())).thenReturn(new ArrayList<>());

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        Optional<Brand> ofResult = Optional.of(brand);
        when(brandRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Brand brand2 = new Brand();
        brand2.setId(2L);
        brand2.setName("com.allene.lease.model.Brand");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(2L);
        model1.setName("com.allene.lease.model.Model");

        ArrayList<Model> modelList = new ArrayList<>();
        modelList.add(model1);
        modelList.add(model);
        ArrayList<ModelDTO> modelDTOList = new ArrayList<>();
        when(modelMapper.asDTOList(any())).thenReturn(modelDTOList);
        when(modelMapper.asEntityList(any())).thenReturn(modelList);
        List<ModelDTO> actualSaveAllResult = modelServiceImpl.saveAll(new ArrayList<>(), 1L);
        assertSame(modelDTOList, actualSaveAllResult);
        assertTrue(actualSaveAllResult.isEmpty());
        verify(modelRepository).saveAll(any());
        verify(brandRepository).findById((Long) any());
        verify(modelMapper).asDTOList(any());
        verify(modelMapper).asEntityList(any());
    }


    @Test
    void shouldDeleteById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);
        doNothing().when(modelRepository).deleteById((Long) any());
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);
        modelServiceImpl.deleteById(1L);
        verify(modelRepository).findById((Long) any());
        verify(modelRepository).deleteById((Long) any());
    }

    @Test
    void shouldNotDeleteById2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);
        doThrow(new DataIntegrityViolationException("Msg")).when(modelRepository).deleteById((Long) any());
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> modelServiceImpl.deleteById(1L));
        verify(modelRepository).findById((Long) any());
        verify(modelRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById3() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);
        doThrow(new AlleneDataIntegrityViolationException("An error occurred")).when(modelRepository)
                .deleteById((Long) any());
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> modelServiceImpl.deleteById(1L));
        verify(modelRepository).findById((Long) any());
        verify(modelRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById4() {
        doNothing().when(modelRepository).deleteById((Long) any());
        when(modelRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> modelServiceImpl.deleteById(1L));
        verify(modelRepository).findById((Long) any());
    }

    @Test
    void shouldFindById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenReturn(modelDTO);
        assertSame(modelDTO, modelServiceImpl.findById(1L));
        verify(modelRepository).findById((Long) any());
        verify(modelMapper).asDTO((Model) any());
    }

    @Test
    void shouldNotFindById2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);
        when(modelMapper.asDTO((Model) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> modelServiceImpl.findById(1L));
        verify(modelRepository).findById((Long) any());
        verify(modelMapper).asDTO((Model) any());
    }


    @Test
    void shouldNotFindById3() {
        when(modelRepository.findById((Long) any())).thenReturn(Optional.empty());

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenReturn(modelDTO);
        assertThrows(ResourceNotFoundException.class, () -> modelServiceImpl.findById(1L));
        verify(modelRepository).findById((Long) any());
    }


    @Test
    void shouldFindAll() {
        when(modelRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        ArrayList<ModelDTO> modelDTOList = new ArrayList<>();
        when(modelMapper.asDTOList((List<Model>) any())).thenReturn(modelDTOList);
        List<ModelDTO> actualFindAllResult = modelServiceImpl.findAll();
        assertSame(modelDTOList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(modelRepository).findAll((Sort) any());
        verify(modelMapper).asDTOList((List<Model>) any());
    }


    @Test
    void shouldNotFindAll2() {
        when(modelRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        when(modelMapper.asDTOList((List<Model>) any())).thenThrow(new DataIntegrityViolationException("id"));
        assertThrows(DataIntegrityViolationException.class, () -> modelServiceImpl.findAll());
        verify(modelRepository).findAll((Sort) any());
        verify(modelMapper).asDTOList((List<Model>) any());
    }


    @Test
    void shouldUpdate() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand1);
        model1.setId(1L);
        model1.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model1);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand2);
        model2.setId(1L);
        model2.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenReturn(modelDTO);
        when(modelMapper.asEntity((ModelDTO) any())).thenReturn(model2);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO1);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");
        assertSame(modelDTO, modelServiceImpl.update(modelDTO1, 1L));
        verify(modelRepository).save((Model) any());
        verify(modelRepository).findById((Long) any());
        verify(modelMapper).asEntity((ModelDTO) any());
        verify(modelMapper).asDTO((Model) any());
        assertEquals(1L, modelDTO1.getId().longValue());
    }

    @Test
    void shouldNotUpdate2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand1);
        model1.setId(1L);
        model1.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model1);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand2);
        model2.setId(1L);
        model2.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(modelMapper.asEntity((ModelDTO) any())).thenReturn(model2);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        assertThrows(AlleneDataIntegrityViolationException.class, () -> modelServiceImpl.update(modelDTO, 1L));
        verify(modelRepository).save((Model) any());
        verify(modelRepository).findById((Long) any());
        verify(modelMapper).asEntity((ModelDTO) any());
        verify(modelMapper).asDTO((Model) any());
    }


    @Test
    void shouldNotUpdate3() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        Optional<Model> ofResult = Optional.of(model);

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand1);
        model1.setId(1L);
        model1.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model1);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand2);
        model2.setId(1L);
        model2.setName("Name");
        when(modelMapper.asDTO((Model) any())).thenThrow(new AlleneDataIntegrityViolationException("An error occurred"));
        when(modelMapper.asEntity((ModelDTO) any())).thenReturn(model2);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        assertThrows(AlleneDataIntegrityViolationException.class, () -> modelServiceImpl.update(modelDTO, 1L));
        verify(modelRepository).save((Model) any());
        verify(modelRepository).findById((Long) any());
        verify(modelMapper).asEntity((ModelDTO) any());
        verify(modelMapper).asDTO((Model) any());
    }

    @Test
    void shouldNotUpdate4() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Model model = new Model();
        model.setBrand(brand);
        model.setId(1L);
        model.setName("Name");
        when(modelRepository.save((Model) any())).thenReturn(model);
        when(modelRepository.findById((Long) any())).thenReturn(Optional.empty());

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand1);
        model1.setId(1L);
        model1.setName("Name");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO);
        modelDTO.setId(1L);
        modelDTO.setName("Name");
        when(modelMapper.asDTO(any())).thenReturn(modelDTO);
        when(modelMapper.asEntity(any())).thenReturn(model1);

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO1);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");
        assertThrows(ResourceNotFoundException.class, () -> modelServiceImpl.update(modelDTO1, 1L));
        verify(modelRepository).findById(any());
        verify(modelMapper).asEntity(any());
    }


    @Test
    void shouldFindAllByBrandId() {
        when(modelRepository.findALlByBrandId(anyLong())).thenReturn(new ArrayList<>());
        ArrayList<ModelDTO> modelDTOList = new ArrayList<>();
        when(modelMapper.asDTOList(any())).thenReturn(modelDTOList);
        List<ModelDTO> actualFindAllByBrandIdResult = modelServiceImpl.findAllByBrandId(1L);
        assertSame(modelDTOList, actualFindAllByBrandIdResult);
        assertTrue(actualFindAllByBrandIdResult.isEmpty());
        verify(modelRepository).findALlByBrandId(anyLong());
        verify(modelMapper).asDTOList(any());
    }


    @Test
    void shouldNotFindAllByBrandId2() {
        when(modelRepository.findALlByBrandId(anyLong())).thenReturn(new ArrayList<>());
        when(modelMapper.asDTOList(any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> modelServiceImpl.findAllByBrandId(1L));
        verify(modelRepository).findALlByBrandId(anyLong());
        verify(modelMapper).asDTOList(any());
    }
}

