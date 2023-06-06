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

import com.allene.lease.dao.ContractRepository;
import com.allene.lease.dao.CustomerRepository;
import com.allene.lease.dao.VehicleRepository;
import com.allene.lease.dto.BrandDTO;
import com.allene.lease.dto.ContractDTO;
import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.dto.ModelDTO;
import com.allene.lease.dto.VehicleDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.ContractMapper;
import com.allene.lease.model.Brand;
import com.allene.lease.model.Contract;
import com.allene.lease.model.Customer;
import com.allene.lease.model.Model;
import com.allene.lease.model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ContractServiceImpl.class})
@ExtendWith(SpringExtension.class)
class ContractServiceImplTest {
    @MockBean
    private ContractMapper contractMapper;

    @MockBean
    private ContractRepository contractRepository;

    @Autowired
    private ContractServiceImpl contractServiceImpl;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private VehicleRepository vehicleRepository;

    @Test
    void shouldSave() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        when(contractRepository.save((Contract) any())).thenReturn(contract);

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
        Optional<Vehicle> ofResult = Optional.of(vehicle1);
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");
        Optional<Customer> ofResult1 = Optional.of(customer1);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult1);

        Customer customer2 = new Customer();
        customer2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setLastName("Doe");

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand5);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand4);
        vehicle2.setId(1L);
        vehicle2.setModel(model2);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");

        Contract contract1 = new Contract();
        contract1.setContractNumber(1L);
        contract1.setCustomer(customer2);
        contract1.setMonthlyRate(BigDecimal.valueOf(42L));
        contract1.setVehicle(vehicle2);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        when(contractMapper.asDTO((Contract) any())).thenReturn(contractDTO);
        when(contractMapper.asEntity((ContractDTO) any())).thenReturn(contract1);

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO1.setFirstName("Jane");
        customerDTO1.setId(1L);
        customerDTO1.setLastName("Doe");

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

        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber(1L);
        contractDTO1.setCustomer(customerDTO1);
        contractDTO1.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO1.setVehicle(vehicleDTO1);
        ContractDTO actualSaveResult = contractServiceImpl.save(contractDTO1);
        assertSame(contractDTO, actualSaveResult);
        assertEquals("42", actualSaveResult.getMonthlyRate().toString());
        assertEquals("42", actualSaveResult.getVehicle().getPrice().toString());
        verify(contractRepository).save((Contract) any());
        verify(vehicleRepository).findById((Long) any());
        verify(customerRepository).findById((Long) any());
        verify(contractMapper).asEntity((ContractDTO) any());
        verify(contractMapper).asDTO((Contract) any());
    }

    @Test
    void shouldNotSave2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        when(contractRepository.save((Contract) any())).thenReturn(contract);

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
        Optional<Vehicle> ofResult = Optional.of(vehicle1);
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");
        Optional<Customer> ofResult1 = Optional.of(customer1);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult1);

        Customer customer2 = new Customer();
        customer2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setLastName("Doe");

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand5);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand4);
        vehicle2.setId(1L);
        vehicle2.setModel(model2);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");

        Contract contract1 = new Contract();
        contract1.setContractNumber(1L);
        contract1.setCustomer(customer2);
        contract1.setMonthlyRate(BigDecimal.valueOf(42L));
        contract1.setVehicle(vehicle2);
        when(contractMapper.asDTO(any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(contractMapper.asEntity(any())).thenReturn(contract1);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        assertThrows(DataIntegrityViolationException.class, () -> contractServiceImpl.save(contractDTO));
        verify(contractRepository).save(any());
        verify(vehicleRepository).findById(any());
        verify(customerRepository).findById(any());
        verify(contractMapper).asEntity(any());
        verify(contractMapper).asDTO(any());
    }

    @Test
    void shouldDeleteById() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        doNothing().when(contractRepository).deleteByContractNumber((Long) any());
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(ofResult);
        contractServiceImpl.deleteById(1L);
        verify(contractRepository).findByContractNumber((Long) any());
        verify(contractRepository).deleteByContractNumber((Long) any());
    }

    @Test
    void shouldNotDeleteById() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        doThrow(new DataIntegrityViolationException("Msg")).when(contractRepository).deleteByContractNumber((Long) any());
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> contractServiceImpl.deleteById(1L));
        verify(contractRepository).findByContractNumber((Long) any());
        verify(contractRepository).deleteByContractNumber((Long) any());
    }

    @Test
    void shouldNotDeleteById2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        doThrow(new AlleneDataIntegrityViolationException("An error occurred")).when(contractRepository)
                .deleteByContractNumber((Long) any());
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> contractServiceImpl.deleteById(1L));
        verify(contractRepository).findByContractNumber((Long) any());
        verify(contractRepository).deleteByContractNumber((Long) any());
    }


    @Test
    void shouldNotDeleteById3() {
        doNothing().when(contractRepository).deleteByContractNumber((Long) any());
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> contractServiceImpl.deleteById(1L));
        verify(contractRepository).findByContractNumber((Long) any());
    }

    @Test
    void shouldFindById() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(ofResult);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        when(contractMapper.asDTO((Contract) any())).thenReturn(contractDTO);
        ContractDTO actualFindByIdResult = contractServiceImpl.findById(1L);
        assertSame(contractDTO, actualFindByIdResult);
        assertEquals("42", actualFindByIdResult.getMonthlyRate().toString());
        assertEquals("42", actualFindByIdResult.getVehicle().getPrice().toString());
        verify(contractRepository).findByContractNumber((Long) any());
        verify(contractMapper).asDTO((Contract) any());
    }

    @Test
    void shouldNotFindById2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(ofResult);
        when(contractMapper.asDTO((Contract) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> contractServiceImpl.findById(1L));
        verify(contractRepository).findByContractNumber((Long) any());
        verify(contractMapper).asDTO((Contract) any());
    }

    @Test
    void shouldNotFindById3() {
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(Optional.empty());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        when(contractMapper.asDTO((Contract) any())).thenReturn(contractDTO);
        assertThrows(ResourceNotFoundException.class, () -> contractServiceImpl.findById(1L));
        verify(contractRepository).findByContractNumber((Long) any());
    }

    @Test
    void shouldFindAll() {
        when(contractRepository.findAll()).thenReturn(new ArrayList<>());
        ArrayList<ContractDTO> contractDTOList = new ArrayList<>();
        when(contractMapper.asDTOList((List<Contract>) any())).thenReturn(contractDTOList);
        List<ContractDTO> actualFindAllResult = contractServiceImpl.findAll();
        assertSame(contractDTOList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(contractRepository).findAll();
        verify(contractMapper).asDTOList((List<Contract>) any());
    }


    @Test
    void shouldNotFindAll2() {
        when(contractRepository.findAll()).thenReturn(new ArrayList<>());
        when(contractMapper.asDTOList(any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> contractServiceImpl.findAll());
        verify(contractRepository).findAll();
        verify(contractMapper).asDTOList(any());
    }

    @Test
    void shouldUpdate() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");

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

        Contract contract1 = new Contract();
        contract1.setContractNumber(1L);
        contract1.setCustomer(customer1);
        contract1.setMonthlyRate(BigDecimal.valueOf(42L));
        contract1.setVehicle(vehicle1);
        when(contractRepository.save(any())).thenReturn(contract1);
        when(contractRepository.findByContractNumber(any())).thenReturn(ofResult);

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand5);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand4);
        vehicle2.setId(1L);
        vehicle2.setModel(model2);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");
        Optional<Vehicle> ofResult1 = Optional.of(vehicle2);
        when(vehicleRepository.findById((Long) any())).thenReturn(ofResult1);

        Customer customer2 = new Customer();
        customer2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setLastName("Doe");
        Optional<Customer> ofResult2 = Optional.of(customer2);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult2);

        Customer customer3 = new Customer();
        customer3.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer3.setFirstName("Jane");
        customer3.setId(1L);
        customer3.setLastName("Doe");

        Brand brand6 = new Brand();
        brand6.setId(1L);
        brand6.setName("Name");

        Brand brand7 = new Brand();
        brand7.setId(1L);
        brand7.setName("Name");

        Model model3 = new Model();
        model3.setBrand(brand7);
        model3.setId(1L);
        model3.setName("Name");

        Vehicle vehicle3 = new Vehicle();
        vehicle3.setBrand(brand6);
        vehicle3.setId(1L);
        vehicle3.setModel(model3);
        vehicle3.setModelYear(1);
        vehicle3.setPrice(BigDecimal.valueOf(42L));
        vehicle3.setVin("Vin");

        Contract contract2 = new Contract();
        contract2.setContractNumber(1L);
        contract2.setCustomer(customer3);
        contract2.setMonthlyRate(BigDecimal.valueOf(42L));
        contract2.setVehicle(vehicle3);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        when(contractMapper.asDTO((Contract) any())).thenReturn(contractDTO);
        when(contractMapper.asEntity((ContractDTO) any())).thenReturn(contract2);

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO1.setFirstName("Jane");
        customerDTO1.setId(1L);
        customerDTO1.setLastName("Doe");

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

        ContractDTO contractDTO1 = new ContractDTO();
        contractDTO1.setContractNumber(1L);
        contractDTO1.setCustomer(customerDTO1);
        contractDTO1.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO1.setVehicle(vehicleDTO1);
        ContractDTO actualUpdateResult = contractServiceImpl.update(contractDTO1, 1L);
        assertSame(contractDTO, actualUpdateResult);
        assertEquals("42", actualUpdateResult.getMonthlyRate().toString());
        assertEquals("42", actualUpdateResult.getVehicle().getPrice().toString());
        verify(contractRepository).save(any());
        verify(contractRepository).findByContractNumber((Long) any());
        verify(vehicleRepository).findById(any());
        verify(customerRepository).findById(any());
        verify(contractMapper).asEntity(any());
        verify(contractMapper).asDTO(any());
        assertEquals(1L, contractDTO1.getContractNumber().longValue());
    }


    @Test
    void shouldNotUpdate2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");

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

        Contract contract1 = new Contract();
        contract1.setContractNumber(1L);
        contract1.setCustomer(customer1);
        contract1.setMonthlyRate(BigDecimal.valueOf(42L));
        contract1.setVehicle(vehicle1);
        when(contractRepository.save( any())).thenReturn(contract1);
        when(contractRepository.findByContractNumber( any())).thenReturn(ofResult);

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand5);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand4);
        vehicle2.setId(1L);
        vehicle2.setModel(model2);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");
        Optional<Vehicle> ofResult1 = Optional.of(vehicle2);
        when(vehicleRepository.findById( any())).thenReturn(ofResult1);

        Customer customer2 = new Customer();
        customer2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setLastName("Doe");
        Optional<Customer> ofResult2 = Optional.of(customer2);
        when(customerRepository.findById( any())).thenReturn(ofResult2);

        Customer customer3 = new Customer();
        customer3.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer3.setFirstName("Jane");
        customer3.setId(1L);
        customer3.setLastName("Doe");

        Brand brand6 = new Brand();
        brand6.setId(1L);
        brand6.setName("Name");

        Brand brand7 = new Brand();
        brand7.setId(1L);
        brand7.setName("Name");

        Model model3 = new Model();
        model3.setBrand(brand7);
        model3.setId(1L);
        model3.setName("Name");

        Vehicle vehicle3 = new Vehicle();
        vehicle3.setBrand(brand6);
        vehicle3.setId(1L);
        vehicle3.setModel(model3);
        vehicle3.setModelYear(1);
        vehicle3.setPrice(BigDecimal.valueOf(42L));
        vehicle3.setVin("Vin");

        Contract contract2 = new Contract();
        contract2.setContractNumber(1L);
        contract2.setCustomer(customer3);
        contract2.setMonthlyRate(BigDecimal.valueOf(42L));
        contract2.setVehicle(vehicle3);
        when(contractMapper.asDTO((Contract) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        when(contractMapper.asEntity((ContractDTO) any())).thenReturn(contract2);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> contractServiceImpl.update(contractDTO, 1L));
        verify(contractRepository).save((Contract) any());
        verify(contractRepository).findByContractNumber((Long) any());
        verify(vehicleRepository).findById((Long) any());
        verify(customerRepository).findById((Long) any());
        verify(contractMapper).asEntity((ContractDTO) any());
        verify(contractMapper).asDTO((Contract) any());
    }

    @Test
    void shouldNotUpdate3() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");

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

        Contract contract1 = new Contract();
        contract1.setContractNumber(1L);
        contract1.setCustomer(customer1);
        contract1.setMonthlyRate(BigDecimal.valueOf(42L));
        contract1.setVehicle(vehicle1);
        when(contractRepository.save(any())).thenReturn(contract1);
        when(contractRepository.findByContractNumber( any())).thenReturn(ofResult);

        Brand brand4 = new Brand();
        brand4.setId(1L);
        brand4.setName("Name");

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand5);
        model2.setId(1L);
        model2.setName("Name");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setBrand(brand4);
        vehicle2.setId(1L);
        vehicle2.setModel(model2);
        vehicle2.setModelYear(1);
        vehicle2.setPrice(BigDecimal.valueOf(42L));
        vehicle2.setVin("Vin");
        Optional<Vehicle> ofResult1 = Optional.of(vehicle2);
        when(vehicleRepository.findById( any())).thenReturn(ofResult1);

        Customer customer2 = new Customer();
        customer2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setLastName("Doe");
        Optional<Customer> ofResult2 = Optional.of(customer2);
        when(customerRepository.findById( any())).thenReturn(ofResult2);

        Customer customer3 = new Customer();
        customer3.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer3.setFirstName("Jane");
        customer3.setId(1L);
        customer3.setLastName("Doe");

        Brand brand6 = new Brand();
        brand6.setId(1L);
        brand6.setName("Name");

        Brand brand7 = new Brand();
        brand7.setId(1L);
        brand7.setName("Name");

        Model model3 = new Model();
        model3.setBrand(brand7);
        model3.setId(1L);
        model3.setName("Name");

        Vehicle vehicle3 = new Vehicle();
        vehicle3.setBrand(brand6);
        vehicle3.setId(1L);
        vehicle3.setModel(model3);
        vehicle3.setModelYear(1);
        vehicle3.setPrice(BigDecimal.valueOf(42L));
        vehicle3.setVin("Vin");

        Contract contract2 = new Contract();
        contract2.setContractNumber(1L);
        contract2.setCustomer(customer3);
        contract2.setMonthlyRate(BigDecimal.valueOf(42L));
        contract2.setVehicle(vehicle3);
        when(contractMapper.asDTO((Contract) any()))
                .thenThrow(new AlleneDataIntegrityViolationException("An error occurred"));
        when(contractMapper.asEntity( any())).thenReturn(contract2);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> contractServiceImpl.update(contractDTO, 1L));
        verify(contractRepository).save(any());
        verify(contractRepository).findByContractNumber( any());
        verify(vehicleRepository).findById( any());
        verify(customerRepository).findById(any());
        verify(contractMapper).asEntity( any());
        verify(contractMapper).asDTO( any());
    }

    @Test
    void shouldFindByContractNum() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        when(contractRepository.findByContractNumber(any())).thenReturn(ofResult);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        when(contractMapper.asDTO( any())).thenReturn(contractDTO);
        ContractDTO actualFindByContractNumResult = contractServiceImpl.findByContractNum(1L);
        assertSame(contractDTO, actualFindByContractNumResult);
        assertEquals("42", actualFindByContractNumResult.getMonthlyRate().toString());
        assertEquals("42", actualFindByContractNumResult.getVehicle().getPrice().toString());
        verify(contractRepository).findByContractNumber( any());
        verify(contractMapper).asDTO( any());
    }

    @Test
    void shouldNotFindByContractNum2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");

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

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        Optional<Contract> ofResult = Optional.of(contract);
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(ofResult);
        when(contractMapper.asDTO((Contract) any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> contractServiceImpl.findByContractNum(1L));
        verify(contractRepository).findByContractNumber((Long) any());
        verify(contractMapper).asDTO((Contract) any());
    }

    @Test
    void shouldNotFindByContractNum3() {
        when(contractRepository.findByContractNumber((Long) any())).thenReturn(Optional.empty());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

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

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        when(contractMapper.asDTO((Contract) any())).thenReturn(contractDTO);
        assertThrows(ResourceNotFoundException.class, () -> contractServiceImpl.findByContractNum(1L));
        verify(contractRepository).findByContractNumber((Long) any());
    }
}

