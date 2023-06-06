package com.allene.lease.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.allene.lease.dao.ModelRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.dto.ModelDTO;
import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.VehicleMapper;
import com.allene.lease.model.Brand;
import com.allene.lease.model.Model;
import com.allene.lease.model.Vehicle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VehicleServiceImpl.class})
@ExtendWith(SpringExtension.class)
class VehicleServiceImplTest {
    @MockBean
    private ModelRepository modelRepository;

    @MockBean
    private VehicleMapper vehicleMapper;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleServiceImpl vehicleServiceImpl;


    @Test
    void shouldSave() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        when(vehicleRepository.save((Vehicle) any())).thenReturn(vehicle);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");
        Optional<Model> ofResult = Optional.of(model1);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand3 = new Brand();
        brand3.setId(1L);
        brand3.setName("Name");

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand4);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand(brand3);
        vehicle1.setId(1L);
        vehicle1.setModel(model2);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        when(vehicleMapper.asDTO((Vehicle) any())).thenReturn(vehicleDTO);
        when(vehicleMapper.asEntity((VehicleDTO) any())).thenReturn(vehicle1);

        BrandDTO brandDTO2 = new BrandDTO();
        brandDTO2.setId(1L);
        brandDTO2.setName("Name");

        BrandDTO brandDTO3 = new BrandDTO();
        brandDTO3.setId(1L);
        brandDTO3.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO3);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");

        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setBrand(brandDTO2);
        vehicleDTO1.setId(1L);
        vehicleDTO1.setModel(modelDTO1);
        vehicleDTO1.setModelYear(1);
        vehicleDTO1.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO1.setVin("Vin");
        VehicleDTO actualSaveResult = vehicleServiceImpl.save(vehicleDTO1);
        assertSame(vehicleDTO, actualSaveResult);
        assertEquals("42", actualSaveResult.getPrice().toString());
        verify(vehicleRepository).save((Vehicle) any());
        verify(modelRepository).findById((Long) any());
        verify(vehicleMapper).asEntity((VehicleDTO) any());
        verify(vehicleMapper).asDTO((Vehicle) any());
    }


    @Test
    void shouldNotSave2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        when(vehicleRepository.save( any())).thenReturn(vehicle);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");
        Optional<Model> ofResult = Optional.of(model1);
        when(modelRepository.findById(any())).thenReturn(ofResult);

        Brand brand3 = new Brand();
        brand3.setId(1L);
        brand3.setName("Name");

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand4);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand(brand3);
        vehicle1.setId(1L);
        vehicle1.setModel(model2);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");
        when(vehicleMapper.asDTO((Vehicle) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(vehicleMapper.asEntity((VehicleDTO) any())).thenReturn(vehicle1);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.save(vehicleDTO));
        verify(vehicleRepository).save((Vehicle) any());
        verify(modelRepository).findById((Long) any());
        verify(vehicleMapper).asEntity((VehicleDTO) any());
        verify(vehicleMapper).asDTO((Vehicle) any());
    }

    @Test
    void shouldDeleteById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);
        doNothing().when(vehicleRepository).deleteById((Long) any());
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult);
        vehicleServiceImpl.deleteById(1L);
        verify(vehicleRepository).findById((Long) any());
        verify(vehicleRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);
        doThrow(new ResourceNotFoundException("An error occurred")).when(vehicleRepository).deleteById((Long) any());
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.deleteById(1L));
        verify(vehicleRepository).findById((Long) any());
        verify(vehicleRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById3() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);
        doThrow(new DataIntegrityViolationException("Msg")).when(vehicleRepository).deleteById((Long) any());
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> vehicleServiceImpl.deleteById(1L));
        verify(vehicleRepository).findById((Long) any());
        verify(vehicleRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById4() {
        doNothing().when(vehicleRepository).deleteById((Long) any());
        when(vehicleRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.deleteById(1L));
        verify(vehicleRepository).findById((Long) any());
    }

    @Test
    void shouldFindById() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);
        when(vehicleRepository.findById(any())).thenReturn(ofResult);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        when(vehicleMapper.asDTO( any())).thenReturn(vehicleDTO);
        VehicleDTO actualFindByIdResult = vehicleServiceImpl.findById(1L);
        assertSame(vehicleDTO, actualFindByIdResult);
        assertEquals("42", actualFindByIdResult.getPrice().toString());
        verify(vehicleRepository).findById( any());
        verify(vehicleMapper).asDTO( any());
    }

    @Test
    void shouldFindById2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);
        when(vehicleRepository.findById( any())).thenReturn(ofResult);
        when(vehicleMapper.asDTO( any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.findById(1L));
        verify(vehicleRepository).findById( any());
        verify(vehicleMapper).asDTO( any());
    }


    @Test
    void shouldNotFindById3() {
        when(vehicleRepository.findById((Long) any())).thenReturn(Optional.empty());

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        when(vehicleMapper.asDTO( any())).thenReturn(vehicleDTO);
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.findById(1L));
        verify(vehicleRepository).findById( any());
    }

    @Test
    void shouldFindAll() {
        when(vehicleRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        ArrayList<VehicleDTO> vehicleDTOList = new ArrayList<>();
        when(vehicleMapper.asDTOList(any())).thenReturn(vehicleDTOList);
        List<VehicleDTO> actualFindAllResult = vehicleServiceImpl.findAll();
        assertSame(vehicleDTOList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(vehicleRepository).findAll((Sort) any());
        verify(vehicleMapper).asDTOList( any());
    }


    @Test
    void shouldNotFindAll2() {
        when(vehicleRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        when(vehicleMapper.asDTOList(any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.findAll());
        verify(vehicleRepository).findAll((Sort) any());
        verify(vehicleMapper).asDTOList(any());
    }


    @Test
    void shouldUpdate() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Brand brand3 = new Brand();
        brand3.setId(1L);
        brand3.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand3);
        model1.setId(1L);
        model1.setName("Name");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand(brand2);
        vehicle1.setId(1L);
        vehicle1.setModel(model1);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");
        when(vehicleRepository.save( any())).thenReturn(vehicle1);
        when(vehicleRepository.findById( any())).thenReturn(ofResult);

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand4);
        model2.setId(1L);
        model2.setName("Name");
        Optional<Model> ofResult1 = Optional.of(model2);
        when(modelRepository.findById( any())).thenReturn(ofResult1);

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Brand brand6 = new Brand();
        brand6.setId(1L);
        brand6.setName("Name");

        Model model3 = new Model();
        model3.setBrand(brand6);
        model3.setId(1L);
        model3.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand5);
        vehicle2.setId(1L);
        vehicle2.setModel(model3);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        when(vehicleMapper.asDTO( any())).thenReturn(vehicleDTO);
        when(vehicleMapper.asEntity( any())).thenReturn(vehicle2);

        BrandDTO brandDTO2 = new BrandDTO();
        brandDTO2.setId(1L);
        brandDTO2.setName("Name");

        BrandDTO brandDTO3 = new BrandDTO();
        brandDTO3.setId(1L);
        brandDTO3.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO3);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");

        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setBrand(brandDTO2);
        vehicleDTO1.setId(1L);
        vehicleDTO1.setModel(modelDTO1);
        vehicleDTO1.setModelYear(1);
        vehicleDTO1.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO1.setVin("Vin");
        VehicleDTO actualUpdateResult = vehicleServiceImpl.update(vehicleDTO1, 1L);
        assertSame(vehicleDTO, actualUpdateResult);
        assertEquals("42", actualUpdateResult.getPrice().toString());
        verify(vehicleRepository).save( any());
        verify(vehicleRepository).findById( any());
        verify(modelRepository).findById(any());
        verify(vehicleMapper).asEntity(any());
        verify(vehicleMapper).asDTO( any());
        assertEquals(1L, vehicleDTO1.getId().longValue());
    }

    @Test
    void shouldNotUpdate2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Brand brand3 = new Brand();
        brand3.setId(1L);
        brand3.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand3);
        model1.setId(1L);
        model1.setName("Name");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand(brand2);
        vehicle1.setId(1L);
        vehicle1.setModel(model1);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");
        when(vehicleRepository.save( any())).thenReturn(vehicle1);
        when(vehicleRepository.findById( any())).thenReturn(ofResult);

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand4);
        model2.setId(1L);
        model2.setName("Name");
        Optional<Model> ofResult1 = Optional.of(model2);
        when(modelRepository.findById( any())).thenReturn(ofResult1);

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Brand brand6 = new Brand();
        brand6.setId(1L);
        brand6.setName("Name");

        Model model3 = new Model();
        model3.setBrand(brand6);
        model3.setId(1L);
        model3.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand5);
        vehicle2.setId(1L);
        vehicle2.setModel(model3);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");
        when(vehicleMapper.asDTO(any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(vehicleMapper.asEntity(any())).thenReturn(vehicle2);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.update(vehicleDTO, 1L));
        verify(vehicleRepository).save((Vehicle) any());
        verify(vehicleRepository).findById((Long) any());
        verify(modelRepository).findById((Long) any());
        verify(vehicleMapper).asEntity((VehicleDTO) any());
        verify(vehicleMapper).asDTO((Vehicle) any());
    }

    @Test
    void shouldNotUpdate3() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        Optional<Vehicle> ofResult = Optional.of(vehicle);

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Brand brand3 = new Brand();
        brand3.setId(1L);
        brand3.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand3);
        model1.setId(1L);
        model1.setName("Name");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand(brand2);
        vehicle1.setId(1L);
        vehicle1.setModel(model1);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");
        when(vehicleRepository.save((Vehicle) any())).thenReturn(vehicle1);
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand4);
        model2.setId(1L);
        model2.setName("Name");
        Optional<Model> ofResult1 = Optional.of(model2);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult1);

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Brand brand6 = new Brand();
        brand6.setId(1L);
        brand6.setName("Name");

        Model model3 = new Model();
        model3.setBrand(brand6);
        model3.setId(1L);
        model3.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand5);
        vehicle2.setId(1L);
        vehicle2.setModel(model3);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");
        when(vehicleMapper.asDTO((Vehicle) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(vehicleMapper.asEntity((VehicleDTO) any())).thenReturn(vehicle2);

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        assertThrows(AlleneDataIntegrityViolationException.class, () -> vehicleServiceImpl.update(vehicleDTO, 1L));
        verify(vehicleRepository).save((Vehicle) any());
        verify(vehicleRepository).findById((Long) any());
        verify(modelRepository).findById((Long) any());
        verify(vehicleMapper).asEntity((VehicleDTO) any());
        verify(vehicleMapper).asDTO((Vehicle) any());
    }


    @Test
    void shoulNotdUpdate4() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        Brand brand1 = new Brand();
        brand1.setId(1L);
        brand1.setName("Name");

        Model model = new Model();
        model.setBrand(brand1);
        model.setId(1L);
        model.setName("Name");

        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setId(1L);
        vehicle.setModel(model);
        vehicle.setModelYear(1);
        vehicle.setPrice(BigDecimal.valueOf(42L));
        vehicle.setVin("Vin");
        when(vehicleRepository.save((Vehicle) any())).thenReturn(vehicle);
        when(vehicleRepository.findById((Long) any())).thenReturn(Optional.empty());

        Brand brand2 = new Brand();
        brand2.setId(1L);
        brand2.setName("Name");

        Model model1 = new Model();
        model1.setBrand(brand2);
        model1.setId(1L);
        model1.setName("Name");
        Optional<Model> ofResult = Optional.of(model1);
        when(modelRepository.findById((Long) any())).thenReturn(ofResult);

        Brand brand3 = new Brand();
        brand3.setId(1L);
        brand3.setName("Name");

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand4);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setBrand(brand3);
        vehicle1.setId(1L);
        vehicle1.setModel(model2);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        BrandDTO brandDTO1 = new BrandDTO();
        brandDTO1.setId(1L);
        brandDTO1.setName("Name");

        ModelDTO modelDTO = new ModelDTO();
        modelDTO.setBrandDTO(brandDTO1);
        modelDTO.setId(1L);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        vehicleDTO.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO.setVin("Vin");
        when(vehicleMapper.asDTO((Vehicle) any())).thenReturn(vehicleDTO);
        when(vehicleMapper.asEntity((VehicleDTO) any())).thenReturn(vehicle1);

        BrandDTO brandDTO2 = new BrandDTO();
        brandDTO2.setId(1L);
        brandDTO2.setName("Name");

        BrandDTO brandDTO3 = new BrandDTO();
        brandDTO3.setId(1L);
        brandDTO3.setName("Name");

        ModelDTO modelDTO1 = new ModelDTO();
        modelDTO1.setBrandDTO(brandDTO3);
        modelDTO1.setId(1L);
        modelDTO1.setName("Name");

        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setBrand(brandDTO2);
        vehicleDTO1.setId(1L);
        vehicleDTO1.setModel(modelDTO1);
        vehicleDTO1.setModelYear(1);
        vehicleDTO1.setPrice(BigDecimal.valueOf(42L));
        vehicleDTO1.setVin("Vin");
        assertThrows(ResourceNotFoundException.class, () -> vehicleServiceImpl.update(vehicleDTO1, 1L));
        verify(vehicleRepository).findById((Long) any());
    }
}
