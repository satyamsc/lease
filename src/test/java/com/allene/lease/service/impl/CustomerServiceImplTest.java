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

import com.allene.lease.dao.CustomerRepository;
import com.allene.lease.dto.CustomerDTO;
import com.allene.lease.exception.AlleneDataIntegrityViolationException;
import com.allene.lease.exception.ResourceNotFoundException;
import com.allene.lease.mapper.CustomerMapper;
import com.allene.lease.model.Customer;

import java.time.LocalDate;
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

@ContextConfiguration(classes = {CustomerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;


    @Test
    void shouldSave() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        when(customerRepository.save((Customer) any())).thenReturn(customer);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        when(customerMapper.asEntity((CustomerDTO) any())).thenReturn(customer1);
        when(customerMapper.asDTO((Customer) any())).thenReturn(customerDTO);

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO1.setFirstName("Jane");
        customerDTO1.setId(1L);
        customerDTO1.setLastName("Doe");
        assertSame(customerDTO, customerServiceImpl.save(customerDTO1));
        verify(customerRepository).save((Customer) any());
        verify(customerMapper).asEntity((CustomerDTO) any());
        verify(customerMapper).asDTO((Customer) any());
    }

    @Test
    void shouldNotSave2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        when(customerRepository.save((Customer) any())).thenReturn(customer);
        when(customerMapper.asEntity((CustomerDTO) any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(customerMapper.asDTO((Customer) any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.save(customerDTO));
        verify(customerMapper).asEntity((CustomerDTO) any());
    }

    @Test
    void shouldDeleteById() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);
        doNothing().when(customerRepository).deleteById((Long) any());
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);
        customerServiceImpl.deleteById(1L);
        verify(customerRepository).findById((Long) any());
        verify(customerRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);
        doThrow(new DataIntegrityViolationException("Msg")).when(customerRepository).deleteById((Long) any());
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> customerServiceImpl.deleteById(1L));
        verify(customerRepository).findById((Long) any());
        verify(customerRepository).deleteById((Long) any());
    }

    @Test
    void shouldNotDeleteById3() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);
        doThrow(new AlleneDataIntegrityViolationException("An error occurred")).when(customerRepository)
                .deleteById((Long) any());
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(AlleneDataIntegrityViolationException.class, () -> customerServiceImpl.deleteById(1L));
        verify(customerRepository).findById((Long) any());
        verify(customerRepository).deleteById((Long) any());
    }


    @Test
    void shouldNotDeleteById4() {
        doNothing().when(customerRepository).deleteById((Long) any());
        when(customerRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.deleteById(1L));
        verify(customerRepository).findById((Long) any());
    }


    @Test
    void shouldFindById() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        when(customerMapper.asDTO((Customer) any())).thenReturn(customerDTO);
        assertSame(customerDTO, customerServiceImpl.findById(1L));
        verify(customerRepository).findById((Long) any());
        verify(customerMapper).asDTO((Customer) any());
    }

    @Test
    void shouldNotFindById2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);
        when(customerRepository.findById(any())).thenReturn(ofResult);
        when(customerMapper.asDTO(any())).thenThrow(new DataIntegrityViolationException("Msg"));
        assertThrows(DataIntegrityViolationException.class, () -> customerServiceImpl.findById(1L));
        verify(customerRepository).findById(any());
        verify(customerMapper).asDTO(any());
    }


    @Test
    void shouldNotFindById3() {
        when(customerRepository.findById(any())).thenReturn(Optional.empty());

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        when(customerMapper.asDTO(any())).thenReturn(customerDTO);
        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.findById(1L));
        verify(customerRepository).findById(any());
    }

    @Test
    void shouldFindAll() {
        when(customerRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        ArrayList<CustomerDTO> customerDTOList = new ArrayList<>();
        when(customerMapper.asDTOList(any())).thenReturn(customerDTOList);
        List<CustomerDTO> actualFindAllResult = customerServiceImpl.findAll();
        assertSame(customerDTOList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(customerRepository).findAll((Sort) any());
        verify(customerMapper).asDTOList(any());
    }

    /**
     * Method under test: {@link CustomerServiceImpl#findAll()}
     */
    @Test
    void shouldNotFindAll2() {
        when(customerRepository.findAll((Sort) any())).thenReturn(new ArrayList<>());
        when(customerMapper.asDTOList(any())).thenThrow(new DataIntegrityViolationException("id"));
        assertThrows(DataIntegrityViolationException.class, () -> customerServiceImpl.findAll());
        verify(customerRepository).findAll((Sort) any());
        verify(customerMapper).asDTOList(any());
    }

    @Test
    void shouldUpdate() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");
        when(customerRepository.save((Customer) any())).thenReturn(customer1);
        when(customerRepository.findById((Long) any())).thenReturn(ofResult);

        Customer customer2 = new Customer();
        customer2.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer2.setFirstName("Jane");
        customer2.setId(1L);
        customer2.setLastName("Doe");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        when(customerMapper.asEntity(any())).thenReturn(customer2);
        when(customerMapper.asDTO(any())).thenReturn(customerDTO);

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO1.setFirstName("Jane");
        customerDTO1.setId(1L);
        customerDTO1.setLastName("Doe");
        assertSame(customerDTO, customerServiceImpl.update(customerDTO1, 1L));
        verify(customerRepository).save(any());
        verify(customerRepository).findById(any());
        verify(customerMapper).asEntity(any());
        verify(customerMapper).asDTO(any());
        assertEquals(1L, customerDTO1.getId().longValue());
    }

    @Test
    void shouldNotUpdate2() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        Optional<Customer> ofResult = Optional.of(customer);

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");
        when(customerRepository.save(any())).thenReturn(customer1);
        when(customerRepository.findById(any())).thenReturn(ofResult);
        when(customerMapper.asEntity(any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(customerMapper.asDTO(any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.update(customerDTO, 1L));
        verify(customerRepository).findById(any());
        verify(customerMapper).asEntity(any());
    }

    @Test
    void shouldNotUpdate3() {
        Customer customer = new Customer();
        customer.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer.setFirstName("Jane");
        customer.setId(1L);
        customer.setLastName("Doe");
        when(customerRepository.save((Customer) any())).thenReturn(customer);
        when(customerRepository.findById((Long) any())).thenReturn(Optional.empty());

        Customer customer1 = new Customer();
        customer1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customer1.setFirstName("Jane");
        customer1.setId(1L);
        customer1.setLastName("Doe");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO.setFirstName("Jane");
        customerDTO.setId(1L);
        customerDTO.setLastName("Doe");
        when(customerMapper.asEntity(any())).thenReturn(customer1);
        when(customerMapper.asDTO(any())).thenReturn(customerDTO);

        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setDateOfBirth(LocalDate.ofEpochDay(1L));
        customerDTO1.setFirstName("Jane");
        customerDTO1.setId(1L);
        customerDTO1.setLastName("Doe");
        assertThrows(ResourceNotFoundException.class, () -> customerServiceImpl.update(customerDTO1, 1L));
        verify(customerRepository).findById((Long) any());
    }
}

