package com.allene.lease.mapper;

import com.allene.lease.dto.*;
import com.allene.lease.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {ContractMapperImpl.class})
@ExtendWith(SpringExtension.class)
class ContractMapperImplTest {
    @Autowired
    private ContractMapperImpl contractMapperImpl;

    @Test
    void shouldAsDTO() {
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        vehicle.setPrice(valueOfResult);
        vehicle.setVin("Vin");

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);
        ContractDTO actualAsDTOResult = contractMapperImpl.asDTO(contract);
        assertEquals(1L, actualAsDTOResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsDTOResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer1 = actualAsDTOResult.getCustomer();
        assertEquals("Doe", customer1.getLastName());
        assertEquals(1L, customer1.getId().longValue());
        assertEquals("Jane", customer1.getFirstName());
        assertEquals("1970-01-02", customer1.getDateOfBirth().toString());
        VehicleDTO vehicle1 = actualAsDTOResult.getVehicle();
        BigDecimal price = vehicle1.getPrice();
        assertEquals(monthlyRate, price);
        assertEquals(1, vehicle1.getModelYear().intValue());
        assertEquals("Vin", vehicle1.getVin());
        assertEquals(1L, vehicle1.getId().longValue());
        ModelDTO model1 = vehicle1.getModel();
        assertEquals("Name", model1.getName());
        BrandDTO brand2 = vehicle1.getBrand();
        assertEquals("Name", brand2.getName());
        assertEquals(1L, brand2.getId().longValue());
        assertEquals(1L, model1.getId().longValue());
        assertEquals("42", price.toString());
    }


    @Test
    void shouldAsDTO2() {
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
        Contract contract = mock(Contract.class);
        when(contract.getCustomer()).thenReturn(customer1);
        when(contract.getVehicle()).thenReturn(vehicle1);
        when(contract.getContractNumber()).thenReturn(1L);
        when(contract.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contract).setContractNumber((Long) any());
        doNothing().when(contract).setCustomer((Customer) any());
        doNothing().when(contract).setMonthlyRate((BigDecimal) any());
        doNothing().when(contract).setVehicle((Vehicle) any());
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contract.setMonthlyRate(valueOfResult);
        contract.setVehicle(vehicle);
        ContractDTO actualAsDTOResult = contractMapperImpl.asDTO(contract);
        assertEquals(1L, actualAsDTOResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsDTOResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer2 = actualAsDTOResult.getCustomer();
        assertEquals("Doe", customer2.getLastName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Jane", customer2.getFirstName());
        assertEquals("1970-01-02", customer2.getDateOfBirth().toString());
        VehicleDTO vehicle2 = actualAsDTOResult.getVehicle();
        BigDecimal price = vehicle2.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle2.getModelYear().intValue());
        assertEquals("Vin", vehicle2.getVin());
        assertEquals(1L, vehicle2.getId().longValue());
        ModelDTO model2 = vehicle2.getModel();
        assertEquals("Name", model2.getName());
        BrandDTO brand4 = vehicle2.getBrand();
        assertEquals("Name", brand4.getName());
        assertEquals(1L, brand4.getId().longValue());
        assertEquals(1L, model2.getId().longValue());
        assertEquals("42", price.toString());
        verify(contract).getCustomer();
        verify(contract).getVehicle();
        verify(contract).getContractNumber();
        verify(contract).getMonthlyRate();
        verify(contract).setContractNumber((Long) any());
        verify(contract).setCustomer((Customer) any());
        verify(contract).setMonthlyRate((BigDecimal) any());
        verify(contract).setVehicle((Vehicle) any());
    }

    @Test
    void shouldAsDTO3() {
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
        Customer customer1 = mock(Customer.class);
        when(customer1.getId()).thenReturn(1L);
        when(customer1.getFirstName()).thenReturn("Jane");
        when(customer1.getLastName()).thenReturn("Doe");
        when(customer1.getDateOfBirth()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(customer1).setDateOfBirth((LocalDate) any());
        doNothing().when(customer1).setFirstName((String) any());
        doNothing().when(customer1).setId((Long) any());
        doNothing().when(customer1).setLastName((String) any());
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
        Contract contract = mock(Contract.class);
        when(contract.getCustomer()).thenReturn(customer1);
        when(contract.getVehicle()).thenReturn(vehicle1);
        when(contract.getContractNumber()).thenReturn(1L);
        when(contract.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contract).setContractNumber((Long) any());
        doNothing().when(contract).setCustomer((Customer) any());
        doNothing().when(contract).setMonthlyRate((BigDecimal) any());
        doNothing().when(contract).setVehicle((Vehicle) any());
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contract.setMonthlyRate(valueOfResult);
        contract.setVehicle(vehicle);
        ContractDTO actualAsDTOResult = contractMapperImpl.asDTO(contract);
        assertEquals(1L, actualAsDTOResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsDTOResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer2 = actualAsDTOResult.getCustomer();
        assertEquals("Doe", customer2.getLastName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Jane", customer2.getFirstName());
        assertEquals("1970-01-02", customer2.getDateOfBirth().toString());
        VehicleDTO vehicle2 = actualAsDTOResult.getVehicle();
        BigDecimal price = vehicle2.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle2.getModelYear().intValue());
        assertEquals("Vin", vehicle2.getVin());
        assertEquals(1L, vehicle2.getId().longValue());
        ModelDTO model2 = vehicle2.getModel();
        assertEquals("Name", model2.getName());
        BrandDTO brand4 = vehicle2.getBrand();
        assertEquals("Name", brand4.getName());
        assertEquals(1L, brand4.getId().longValue());
        assertEquals(1L, model2.getId().longValue());
        assertEquals("42", price.toString());
        verify(contract).getCustomer();
        verify(contract).getVehicle();
        verify(contract).getContractNumber();
        verify(contract).getMonthlyRate();
        verify(contract).setContractNumber((Long) any());
        verify(contract).setCustomer((Customer) any());
        verify(contract).setMonthlyRate((BigDecimal) any());
        verify(contract).setVehicle((Vehicle) any());
        verify(customer1).getId();
        verify(customer1).getFirstName();
        verify(customer1).getLastName();
        verify(customer1).getDateOfBirth();
        verify(customer1).setDateOfBirth((LocalDate) any());
        verify(customer1).setFirstName((String) any());
        verify(customer1).setId((Long) any());
        verify(customer1).setLastName((String) any());
    }

    @Test
    void shouldAsDTO4() {
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
        Customer customer1 = mock(Customer.class);
        when(customer1.getId()).thenReturn(1L);
        when(customer1.getFirstName()).thenReturn("Jane");
        when(customer1.getLastName()).thenReturn("Doe");
        when(customer1.getDateOfBirth()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(customer1).setDateOfBirth((LocalDate) any());
        doNothing().when(customer1).setFirstName((String) any());
        doNothing().when(customer1).setId((Long) any());
        doNothing().when(customer1).setLastName((String) any());
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
        Vehicle vehicle1 = mock(Vehicle.class);
        when(vehicle1.getBrand()).thenReturn(brand4);
        when(vehicle1.getModel()).thenReturn(model2);
        when(vehicle1.getModelYear()).thenReturn(1);
        when(vehicle1.getId()).thenReturn(1L);
        when(vehicle1.getVin()).thenReturn("Vin");
        when(vehicle1.getPrice()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(vehicle1).setBrand((Brand) any());
        doNothing().when(vehicle1).setId((Long) any());
        doNothing().when(vehicle1).setModel((Model) any());
        doNothing().when(vehicle1).setModelYear((Integer) any());
        doNothing().when(vehicle1).setPrice((BigDecimal) any());
        doNothing().when(vehicle1).setVin((String) any());
        vehicle1.setBrand(brand2);
        vehicle1.setId(1L);
        vehicle1.setModel(model1);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");
        Contract contract = mock(Contract.class);
        when(contract.getCustomer()).thenReturn(customer1);
        when(contract.getVehicle()).thenReturn(vehicle1);
        when(contract.getContractNumber()).thenReturn(1L);
        when(contract.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contract).setContractNumber((Long) any());
        doNothing().when(contract).setCustomer((Customer) any());
        doNothing().when(contract).setMonthlyRate((BigDecimal) any());
        doNothing().when(contract).setVehicle((Vehicle) any());
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contract.setMonthlyRate(valueOfResult);
        contract.setVehicle(vehicle);
        ContractDTO actualAsDTOResult = contractMapperImpl.asDTO(contract);
        assertEquals(1L, actualAsDTOResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsDTOResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer2 = actualAsDTOResult.getCustomer();
        assertEquals("Doe", customer2.getLastName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Jane", customer2.getFirstName());
        assertEquals("1970-01-02", customer2.getDateOfBirth().toString());
        VehicleDTO vehicle2 = actualAsDTOResult.getVehicle();
        BigDecimal price = vehicle2.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle2.getModelYear().intValue());
        assertEquals("Vin", vehicle2.getVin());
        assertEquals(1L, vehicle2.getId().longValue());
        ModelDTO model3 = vehicle2.getModel();
        assertEquals("Name", model3.getName());
        BrandDTO brand6 = vehicle2.getBrand();
        assertEquals("Name", brand6.getName());
        assertEquals(1L, brand6.getId().longValue());
        assertEquals(1L, model3.getId().longValue());
        assertEquals("42", price.toString());
        verify(contract).getCustomer();
        verify(contract).getVehicle();
        verify(contract).getContractNumber();
        verify(contract).getMonthlyRate();
        verify(contract).setContractNumber((Long) any());
        verify(contract).setCustomer((Customer) any());
        verify(contract).setMonthlyRate((BigDecimal) any());
        verify(contract).setVehicle((Vehicle) any());
        verify(customer1).getId();
        verify(customer1).getFirstName();
        verify(customer1).getLastName();
        verify(customer1).getDateOfBirth();
        verify(customer1).setDateOfBirth((LocalDate) any());
        verify(customer1).setFirstName((String) any());
        verify(customer1).setId((Long) any());
        verify(customer1).setLastName((String) any());
        verify(vehicle1).getBrand();
        verify(vehicle1).getModel();
        verify(vehicle1).getModelYear();
        verify(vehicle1).getId();
        verify(vehicle1).getVin();
        verify(vehicle1).getPrice();
        verify(vehicle1).setBrand((Brand) any());
        verify(vehicle1).setId((Long) any());
        verify(vehicle1).setModel((Model) any());
        verify(vehicle1).setModelYear((Integer) any());
        verify(vehicle1).setPrice((BigDecimal) any());
        verify(vehicle1).setVin((String) any());
    }

    @Test
    void shouldAsDTO5() {
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
        Customer customer1 = mock(Customer.class);
        when(customer1.getId()).thenReturn(1L);
        when(customer1.getFirstName()).thenReturn("Jane");
        when(customer1.getLastName()).thenReturn("Doe");
        when(customer1.getDateOfBirth()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(customer1).setDateOfBirth((LocalDate) any());
        doNothing().when(customer1).setFirstName((String) any());
        doNothing().when(customer1).setId((Long) any());
        doNothing().when(customer1).setLastName((String) any());
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
        Brand brand4 = mock(Brand.class);
        when(brand4.getName()).thenReturn("Name");
        when(brand4.getId()).thenReturn(1L);
        doNothing().when(brand4).setId(anyLong());
        doNothing().when(brand4).setName((String) any());
        brand4.setId(1L);
        brand4.setName("Name");

        Brand brand5 = new Brand();
        brand5.setId(1L);
        brand5.setName("Name");

        Model model2 = new Model();
        model2.setBrand(brand5);
        model2.setId(1L);
        model2.setName("Name");
        Vehicle vehicle1 = mock(Vehicle.class);
        when(vehicle1.getBrand()).thenReturn(brand4);
        when(vehicle1.getModel()).thenReturn(model2);
        when(vehicle1.getModelYear()).thenReturn(1);
        when(vehicle1.getId()).thenReturn(1L);
        when(vehicle1.getVin()).thenReturn("Vin");
        when(vehicle1.getPrice()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(vehicle1).setBrand((Brand) any());
        doNothing().when(vehicle1).setId((Long) any());
        doNothing().when(vehicle1).setModel((Model) any());
        doNothing().when(vehicle1).setModelYear((Integer) any());
        doNothing().when(vehicle1).setPrice((BigDecimal) any());
        doNothing().when(vehicle1).setVin((String) any());
        vehicle1.setBrand(brand2);
        vehicle1.setId(1L);
        vehicle1.setModel(model1);
        vehicle1.setModelYear(1);
        vehicle1.setPrice(BigDecimal.valueOf(42L));
        vehicle1.setVin("Vin");
        Contract contract = mock(Contract.class);
        when(contract.getCustomer()).thenReturn(customer1);
        when(contract.getVehicle()).thenReturn(vehicle1);
        when(contract.getContractNumber()).thenReturn(1L);
        when(contract.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contract).setContractNumber((Long) any());
        doNothing().when(contract).setCustomer((Customer) any());
        doNothing().when(contract).setMonthlyRate((BigDecimal) any());
        doNothing().when(contract).setVehicle((Vehicle) any());
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contract.setMonthlyRate(valueOfResult);
        contract.setVehicle(vehicle);
        ContractDTO actualAsDTOResult = contractMapperImpl.asDTO(contract);
        assertEquals(1L, actualAsDTOResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsDTOResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer2 = actualAsDTOResult.getCustomer();
        assertEquals("Doe", customer2.getLastName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Jane", customer2.getFirstName());
        assertEquals("1970-01-02", customer2.getDateOfBirth().toString());
        VehicleDTO vehicle2 = actualAsDTOResult.getVehicle();
        BigDecimal price = vehicle2.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle2.getModelYear().intValue());
        assertEquals("Vin", vehicle2.getVin());
        assertEquals(1L, vehicle2.getId().longValue());
        ModelDTO model3 = vehicle2.getModel();
        assertEquals("Name", model3.getName());
        BrandDTO brand6 = vehicle2.getBrand();
        assertEquals("Name", brand6.getName());
        assertEquals(1L, brand6.getId().longValue());
        assertEquals(1L, model3.getId().longValue());
        assertEquals("42", price.toString());
        verify(contract).getCustomer();
        verify(contract).getVehicle();
        verify(contract).getContractNumber();
        verify(contract).getMonthlyRate();
        verify(contract).setContractNumber((Long) any());
        verify(contract).setCustomer((Customer) any());
        verify(contract).setMonthlyRate((BigDecimal) any());
        verify(contract).setVehicle((Vehicle) any());
        verify(customer1).getId();
        verify(customer1).getFirstName();
        verify(customer1).getLastName();
        verify(customer1).getDateOfBirth();
        verify(customer1).setDateOfBirth((LocalDate) any());
        verify(customer1).setFirstName((String) any());
        verify(customer1).setId((Long) any());
        verify(customer1).setLastName((String) any());
        verify(vehicle1).getBrand();
        verify(vehicle1).getModel();
        verify(vehicle1).getModelYear();
        verify(vehicle1).getId();
        verify(vehicle1).getVin();
        verify(vehicle1).getPrice();
        verify(vehicle1).setBrand((Brand) any());
        verify(vehicle1).setId((Long) any());
        verify(vehicle1).setModel((Model) any());
        verify(vehicle1).setModelYear((Integer) any());
        verify(vehicle1).setPrice((BigDecimal) any());
        verify(vehicle1).setVin((String) any());
        verify(brand4).getName();
        verify(brand4).getId();
        verify(brand4).setId(anyLong());
        verify(brand4).setName((String) any());
    }


    @Test
    void shouldAsEntityList() {
        assertTrue(contractMapperImpl.asEntityList(new ArrayList<>()).isEmpty());
    }


    @Test
    void shouldAsEntityList2() {
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        vehicleDTO.setPrice(valueOfResult);
        vehicleDTO.setVin("Vin");

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);

        ArrayList<ContractDTO> contractDTOList = new ArrayList<>();
        contractDTOList.add(contractDTO);
        List<Contract> actualAsEntityListResult = contractMapperImpl.asEntityList(contractDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Contract getResult = actualAsEntityListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        Customer customer = getResult.getCustomer();
        assertEquals("Doe", customer.getLastName());
        assertEquals(1L, customer.getId().longValue());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("1970-01-02", customer.getDateOfBirth().toString());
        Vehicle vehicle = getResult.getVehicle();
        BigDecimal price = vehicle.getPrice();
        assertEquals(monthlyRate, price);
        assertEquals(1, vehicle.getModelYear().intValue());
        assertEquals("Vin", vehicle.getVin());
        assertEquals(1L, vehicle.getId().longValue());
        Model model = vehicle.getModel();
        assertEquals("Name", model.getName());
        Brand brand = vehicle.getBrand();
        assertEquals("Name", brand.getName());
        assertEquals(1L, brand.getId());
        assertEquals(1L, model.getId());
        assertNull(model.getBrand());
        assertEquals("42", price.toString());
    }

    @Test
    void shouldAsEntityList3() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");

        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(null);
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        vehicleDTO.setPrice(valueOfResult);
        vehicleDTO.setVin("Vin");

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);

        ArrayList<ContractDTO> contractDTOList = new ArrayList<>();
        contractDTOList.add(contractDTO);
        List<Contract> actualAsEntityListResult = contractMapperImpl.asEntityList(contractDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Contract getResult = actualAsEntityListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        Customer customer = getResult.getCustomer();
        assertEquals("Doe", customer.getLastName());
        assertEquals(1L, customer.getId().longValue());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("1970-01-02", customer.getDateOfBirth().toString());
        Vehicle vehicle = getResult.getVehicle();
        BigDecimal price = vehicle.getPrice();
        assertEquals(monthlyRate, price);
        assertEquals(1, vehicle.getModelYear().intValue());
        assertEquals("Vin", vehicle.getVin());
        assertEquals(1L, vehicle.getId().longValue());
        Model model = vehicle.getModel();
        assertEquals("Name", model.getName());
        Brand brand = vehicle.getBrand();
        assertEquals("Name", brand.getName());
        assertEquals(0L, brand.getId());
        assertEquals(1L, model.getId());
        assertNull(model.getBrand());
        assertEquals("42", price.toString());
    }


    @Test
    void shouldAsEntityList4() {
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
        modelDTO.setId(null);
        modelDTO.setName("Name");

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setBrand(brandDTO);
        vehicleDTO.setId(1L);
        vehicleDTO.setModel(modelDTO);
        vehicleDTO.setModelYear(1);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        vehicleDTO.setPrice(valueOfResult);
        vehicleDTO.setVin("Vin");

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);

        ArrayList<ContractDTO> contractDTOList = new ArrayList<>();
        contractDTOList.add(contractDTO);
        List<Contract> actualAsEntityListResult = contractMapperImpl.asEntityList(contractDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Contract getResult = actualAsEntityListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        Customer customer = getResult.getCustomer();
        assertEquals("Doe", customer.getLastName());
        assertEquals(1L, customer.getId().longValue());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("1970-01-02", customer.getDateOfBirth().toString());
        Vehicle vehicle = getResult.getVehicle();
        BigDecimal price = vehicle.getPrice();
        assertEquals(monthlyRate, price);
        assertEquals(1, vehicle.getModelYear().intValue());
        assertEquals("Vin", vehicle.getVin());
        assertEquals(1L, vehicle.getId().longValue());
        Model model = vehicle.getModel();
        assertEquals("Name", model.getName());
        Brand brand = vehicle.getBrand();
        assertEquals("Name", brand.getName());
        assertEquals(1L, brand.getId());
        assertEquals(0L, model.getId());
        assertNull(model.getBrand());
        assertEquals("42", price.toString());
    }

    @Test
    void shouldAsEntityList5() {
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
        ContractDTO contractDTO = mock(ContractDTO.class);
        when(contractDTO.getCustomer()).thenReturn(customerDTO1);
        when(contractDTO.getVehicle()).thenReturn(vehicleDTO1);
        when(contractDTO.getContractNumber()).thenReturn(1L);
        when(contractDTO.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contractDTO).setContractNumber((Long) any());
        doNothing().when(contractDTO).setCustomer((CustomerDTO) any());
        doNothing().when(contractDTO).setMonthlyRate((BigDecimal) any());
        doNothing().when(contractDTO).setVehicle((VehicleDTO) any());
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contractDTO.setMonthlyRate(valueOfResult);
        contractDTO.setVehicle(vehicleDTO);

        ArrayList<ContractDTO> contractDTOList = new ArrayList<>();
        contractDTOList.add(contractDTO);
        List<Contract> actualAsEntityListResult = contractMapperImpl.asEntityList(contractDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Contract getResult = actualAsEntityListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        Customer customer = getResult.getCustomer();
        assertEquals("Doe", customer.getLastName());
        assertEquals(1L, customer.getId().longValue());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("1970-01-02", customer.getDateOfBirth().toString());
        Vehicle vehicle = getResult.getVehicle();
        BigDecimal price = vehicle.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle.getModelYear().intValue());
        assertEquals("Vin", vehicle.getVin());
        assertEquals(1L, vehicle.getId().longValue());
        Model model = vehicle.getModel();
        assertEquals("Name", model.getName());
        Brand brand = vehicle.getBrand();
        assertEquals("Name", brand.getName());
        assertEquals(1L, brand.getId());
        assertEquals(1L, model.getId());
        assertNull(model.getBrand());
        assertEquals("42", price.toString());
        verify(contractDTO).getCustomer();
        verify(contractDTO).getVehicle();
        verify(contractDTO).getContractNumber();
        verify(contractDTO).getMonthlyRate();
        verify(contractDTO).setContractNumber((Long) any());
        verify(contractDTO).setCustomer((CustomerDTO) any());
        verify(contractDTO).setMonthlyRate((BigDecimal) any());
        verify(contractDTO).setVehicle((VehicleDTO) any());
    }

    @Test
    void shouldAsDTOList() {
        assertTrue(contractMapperImpl.asDTOList(new ArrayList<>()).isEmpty());
    }

    @Test
    void shouldAsDTOList2() {
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        vehicle.setPrice(valueOfResult);
        vehicle.setVin("Vin");

        Contract contract = new Contract();
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        contract.setMonthlyRate(BigDecimal.valueOf(42L));
        contract.setVehicle(vehicle);

        ArrayList<Contract> contractList = new ArrayList<>();
        contractList.add(contract);
        List<ContractDTO> actualAsDTOListResult = contractMapperImpl.asDTOList(contractList);
        assertEquals(1, actualAsDTOListResult.size());
        ContractDTO getResult = actualAsDTOListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer1 = getResult.getCustomer();
        assertEquals("Doe", customer1.getLastName());
        assertEquals(1L, customer1.getId().longValue());
        assertEquals("Jane", customer1.getFirstName());
        assertEquals("1970-01-02", customer1.getDateOfBirth().toString());
        VehicleDTO vehicle1 = getResult.getVehicle();
        BigDecimal price = vehicle1.getPrice();
        assertEquals(monthlyRate, price);
        assertEquals(1, vehicle1.getModelYear().intValue());
        assertEquals("Vin", vehicle1.getVin());
        assertEquals(1L, vehicle1.getId().longValue());
        ModelDTO model1 = vehicle1.getModel();
        assertEquals("Name", model1.getName());
        BrandDTO brand2 = vehicle1.getBrand();
        assertEquals("Name", brand2.getName());
        assertEquals(1L, brand2.getId().longValue());
        assertEquals(1L, model1.getId().longValue());
        assertEquals("42", price.toString());
    }

    /**
     * Method under test: {@link ContractMapperImpl#asDTOList(List)}
     */
    @Test
    void shouldAsDTOList3() {
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
        Contract contract = mock(Contract.class);
        when(contract.getCustomer()).thenReturn(customer1);
        when(contract.getVehicle()).thenReturn(vehicle1);
        when(contract.getContractNumber()).thenReturn(1L);
        when(contract.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contract).setContractNumber((Long) any());
        doNothing().when(contract).setCustomer((Customer) any());
        doNothing().when(contract).setMonthlyRate((BigDecimal) any());
        doNothing().when(contract).setVehicle((Vehicle) any());
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contract.setMonthlyRate(valueOfResult);
        contract.setVehicle(vehicle);

        ArrayList<Contract> contractList = new ArrayList<>();
        contractList.add(contract);
        List<ContractDTO> actualAsDTOListResult = contractMapperImpl.asDTOList(contractList);
        assertEquals(1, actualAsDTOListResult.size());
        ContractDTO getResult = actualAsDTOListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer2 = getResult.getCustomer();
        assertEquals("Doe", customer2.getLastName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Jane", customer2.getFirstName());
        assertEquals("1970-01-02", customer2.getDateOfBirth().toString());
        VehicleDTO vehicle2 = getResult.getVehicle();
        BigDecimal price = vehicle2.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle2.getModelYear().intValue());
        assertEquals("Vin", vehicle2.getVin());
        assertEquals(1L, vehicle2.getId().longValue());
        ModelDTO model2 = vehicle2.getModel();
        assertEquals("Name", model2.getName());
        BrandDTO brand4 = vehicle2.getBrand();
        assertEquals("Name", brand4.getName());
        assertEquals(1L, brand4.getId().longValue());
        assertEquals(1L, model2.getId().longValue());
        assertEquals("42", price.toString());
        verify(contract).getCustomer();
        verify(contract).getVehicle();
        verify(contract).getContractNumber();
        verify(contract).getMonthlyRate();
        verify(contract).setContractNumber((Long) any());
        verify(contract).setCustomer((Customer) any());
        verify(contract).setMonthlyRate((BigDecimal) any());
        verify(contract).setVehicle((Vehicle) any());
    }

    @Test
    void shouldAsDTOList4() {
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
        Customer customer1 = mock(Customer.class);
        when(customer1.getId()).thenReturn(1L);
        when(customer1.getFirstName()).thenReturn("Jane");
        when(customer1.getLastName()).thenReturn("Doe");
        when(customer1.getDateOfBirth()).thenReturn(LocalDate.ofEpochDay(1L));
        doNothing().when(customer1).setDateOfBirth((LocalDate) any());
        doNothing().when(customer1).setFirstName((String) any());
        doNothing().when(customer1).setId((Long) any());
        doNothing().when(customer1).setLastName((String) any());
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
        Contract contract = mock(Contract.class);
        when(contract.getCustomer()).thenReturn(customer1);
        when(contract.getVehicle()).thenReturn(vehicle1);
        when(contract.getContractNumber()).thenReturn(1L);
        when(contract.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contract).setContractNumber((Long) any());
        doNothing().when(contract).setCustomer((Customer) any());
        doNothing().when(contract).setMonthlyRate((BigDecimal) any());
        doNothing().when(contract).setVehicle((Vehicle) any());
        contract.setContractNumber(1L);
        contract.setCustomer(customer);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contract.setMonthlyRate(valueOfResult);
        contract.setVehicle(vehicle);

        ArrayList<Contract> contractList = new ArrayList<>();
        contractList.add(contract);
        List<ContractDTO> actualAsDTOListResult = contractMapperImpl.asDTOList(contractList);
        assertEquals(1, actualAsDTOListResult.size());
        ContractDTO getResult = actualAsDTOListResult.get(0);
        assertEquals(1L, getResult.getContractNumber().longValue());
        BigDecimal monthlyRate = getResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        CustomerDTO customer2 = getResult.getCustomer();
        assertEquals("Doe", customer2.getLastName());
        assertEquals(1L, customer2.getId().longValue());
        assertEquals("Jane", customer2.getFirstName());
        assertEquals("1970-01-02", customer2.getDateOfBirth().toString());
        VehicleDTO vehicle2 = getResult.getVehicle();
        BigDecimal price = vehicle2.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle2.getModelYear().intValue());
        assertEquals("Vin", vehicle2.getVin());
        assertEquals(1L, vehicle2.getId().longValue());
        ModelDTO model2 = vehicle2.getModel();
        assertEquals("Name", model2.getName());
        BrandDTO brand4 = vehicle2.getBrand();
        assertEquals("Name", brand4.getName());
        assertEquals(1L, brand4.getId().longValue());
        assertEquals(1L, model2.getId().longValue());
        assertEquals("42", price.toString());
        verify(contract).getCustomer();
        verify(contract).getVehicle();
        verify(contract).getContractNumber();
        verify(contract).getMonthlyRate();
        verify(contract).setContractNumber((Long) any());
        verify(contract).setCustomer((Customer) any());
        verify(contract).setMonthlyRate((BigDecimal) any());
        verify(contract).setVehicle((Vehicle) any());
        verify(customer1).getId();
        verify(customer1).getFirstName();
        verify(customer1).getLastName();
        verify(customer1).getDateOfBirth();
        verify(customer1).setDateOfBirth((LocalDate) any());
        verify(customer1).setFirstName((String) any());
        verify(customer1).setId((Long) any());
        verify(customer1).setLastName((String) any());
    }

    @Test
    void shouldAsEntity() {
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
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        vehicleDTO.setPrice(valueOfResult);
        vehicleDTO.setVin("Vin");

        ContractDTO contractDTO = new ContractDTO();
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        contractDTO.setMonthlyRate(BigDecimal.valueOf(42L));
        contractDTO.setVehicle(vehicleDTO);
        Contract actualAsEntityResult = contractMapperImpl.asEntity(contractDTO);
        assertEquals(1L, actualAsEntityResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsEntityResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        Customer customer = actualAsEntityResult.getCustomer();
        assertEquals("Doe", customer.getLastName());
        assertEquals(1L, customer.getId().longValue());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("1970-01-02", customer.getDateOfBirth().toString());
        Vehicle vehicle = actualAsEntityResult.getVehicle();
        BigDecimal price = vehicle.getPrice();
        assertEquals(monthlyRate, price);
        assertEquals(1, vehicle.getModelYear().intValue());
        assertEquals("Vin", vehicle.getVin());
        assertEquals(1L, vehicle.getId().longValue());
        Model model = vehicle.getModel();
        assertEquals("Name", model.getName());
        Brand brand = vehicle.getBrand();
        assertEquals("Name", brand.getName());
        assertEquals(1L, brand.getId());
        assertEquals(1L, model.getId());
        assertNull(model.getBrand());
        assertEquals("42", price.toString());
    }

    @Test
    void shouldAsEntity2() {
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
        ContractDTO contractDTO = mock(ContractDTO.class);
        when(contractDTO.getCustomer()).thenReturn(customerDTO1);
        when(contractDTO.getVehicle()).thenReturn(vehicleDTO1);
        when(contractDTO.getContractNumber()).thenReturn(1L);
        when(contractDTO.getMonthlyRate()).thenReturn(BigDecimal.valueOf(42L));
        doNothing().when(contractDTO).setContractNumber((Long) any());
        doNothing().when(contractDTO).setCustomer((CustomerDTO) any());
        doNothing().when(contractDTO).setMonthlyRate((BigDecimal) any());
        doNothing().when(contractDTO).setVehicle((VehicleDTO) any());
        contractDTO.setContractNumber(1L);
        contractDTO.setCustomer(customerDTO);
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        contractDTO.setMonthlyRate(valueOfResult);
        contractDTO.setVehicle(vehicleDTO);
        Contract actualAsEntityResult = contractMapperImpl.asEntity(contractDTO);
        assertEquals(1L, actualAsEntityResult.getContractNumber().longValue());
        BigDecimal monthlyRate = actualAsEntityResult.getMonthlyRate();
        assertEquals(valueOfResult, monthlyRate);
        assertEquals("42", monthlyRate.toString());
        Customer customer = actualAsEntityResult.getCustomer();
        assertEquals("Doe", customer.getLastName());
        assertEquals(1L, customer.getId().longValue());
        assertEquals("Jane", customer.getFirstName());
        assertEquals("1970-01-02", customer.getDateOfBirth().toString());
        Vehicle vehicle = actualAsEntityResult.getVehicle();
        BigDecimal price = vehicle.getPrice();
        assertEquals(valueOfResult, price);
        assertEquals(1, vehicle.getModelYear().intValue());
        assertEquals("Vin", vehicle.getVin());
        assertEquals(1L, vehicle.getId().longValue());
        Model model = vehicle.getModel();
        assertEquals("Name", model.getName());
        Brand brand = vehicle.getBrand();
        assertEquals("Name", brand.getName());
        assertEquals(1L, brand.getId());
        assertEquals(1L, model.getId());
        assertNull(model.getBrand());
        assertEquals("42", price.toString());
        verify(contractDTO).getCustomer();
        verify(contractDTO).getVehicle();
        verify(contractDTO).getContractNumber();
        verify(contractDTO).getMonthlyRate();
        verify(contractDTO).setContractNumber((Long) any());
        verify(contractDTO).setCustomer((CustomerDTO) any());
        verify(contractDTO).setMonthlyRate((BigDecimal) any());
        verify(contractDTO).setVehicle((VehicleDTO) any());
    }
}
