package com.allene.lease.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.allene.lease.dto.BrandDTO;
import com.allene.lease.model.Brand;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BrandMapperImpl.class})
@ExtendWith(SpringExtension.class)
class BrandMapperImplTest {
    @Autowired
    private BrandMapperImpl brandMapperImpl;


    @Test
    void shouldAsDTO() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");
        BrandDTO actualAsDTOResult = brandMapperImpl.asDTO(brand);
        assertEquals(1L, actualAsDTOResult.getId().longValue());
        assertEquals("Name", actualAsDTOResult.getName());
    }

    @Test
    void shouldAsDTO2() {
        Brand brand = mock(Brand.class);
        when(brand.getName()).thenReturn("Name");
        when(brand.getId()).thenReturn(1L);
        doNothing().when(brand).setId(anyLong());
        doNothing().when(brand).setName( any());
        brand.setId(1L);
        brand.setName("Name");
        BrandDTO actualAsDTOResult = brandMapperImpl.asDTO(brand);
        assertEquals(1L, actualAsDTOResult.getId().longValue());
        assertEquals("Name", actualAsDTOResult.getName());
        verify(brand).getName();
        verify(brand).getId();
        verify(brand).setId(anyLong());
        verify(brand).setName( any());
    }


    @Test
    void shouldAsEntityList() {
        assertTrue(brandMapperImpl.asEntityList(new ArrayList<>()).isEmpty());
    }

    @Test
    void shouldAsEntityList2() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ArrayList<BrandDTO> brandDTOList = new ArrayList<>();
        brandDTOList.add(brandDTO);
        List<Brand> actualAsEntityListResult = brandMapperImpl.asEntityList(brandDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Brand getResult = actualAsEntityListResult.get(0);
        assertEquals(1L, getResult.getId());
        assertEquals("Name", getResult.getName());
    }

    @Test
    void shouldAsEntityList3() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(null);
        brandDTO.setName("Name");

        ArrayList<BrandDTO> brandDTOList = new ArrayList<>();
        brandDTOList.add(brandDTO);
        List<Brand> actualAsEntityListResult = brandMapperImpl.asEntityList(brandDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Brand getResult = actualAsEntityListResult.get(0);
        assertEquals(0L, getResult.getId());
        assertEquals("Name", getResult.getName());
    }


    @Test
    void shouldAsEntityList4() {
        BrandDTO brandDTO = mock(BrandDTO.class);
        when(brandDTO.getId()).thenReturn(1L);
        when(brandDTO.getName()).thenReturn("Name");
        doNothing().when(brandDTO).setId((Long) any());
        doNothing().when(brandDTO).setName( any());
        brandDTO.setId(1L);
        brandDTO.setName("Name");

        ArrayList<BrandDTO> brandDTOList = new ArrayList<>();
        brandDTOList.add(brandDTO);
        List<Brand> actualAsEntityListResult = brandMapperImpl.asEntityList(brandDTOList);
        assertEquals(1, actualAsEntityListResult.size());
        Brand getResult = actualAsEntityListResult.get(0);
        assertEquals(1L, getResult.getId());
        assertEquals("Name", getResult.getName());
        verify(brandDTO, atLeast(1)).getId();
        verify(brandDTO).getName();
        verify(brandDTO).setId( any());
        verify(brandDTO).setName(any());
    }

    @Test
    void shouldAsDTOList() {
        assertTrue(brandMapperImpl.asDTOList(new ArrayList<>()).isEmpty());
    }


    @Test
    void shouldAsDTOList2() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Name");

        ArrayList<Brand> brandList = new ArrayList<>();
        brandList.add(brand);
        List<BrandDTO> actualAsDTOListResult = brandMapperImpl.asDTOList(brandList);
        assertEquals(1, actualAsDTOListResult.size());
        BrandDTO getResult = actualAsDTOListResult.get(0);
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("Name", getResult.getName());
    }


    @Test
    void shouldAsDTOList3() {
        Brand brand = mock(Brand.class);
        when(brand.getName()).thenReturn("Name");
        when(brand.getId()).thenReturn(1L);
        doNothing().when(brand).setId(anyLong());
        doNothing().when(brand).setName((String) any());
        brand.setId(1L);
        brand.setName("Name");

        ArrayList<Brand> brandList = new ArrayList<>();
        brandList.add(brand);
        List<BrandDTO> actualAsDTOListResult = brandMapperImpl.asDTOList(brandList);
        assertEquals(1, actualAsDTOListResult.size());
        BrandDTO getResult = actualAsDTOListResult.get(0);
        assertEquals(1L, getResult.getId().longValue());
        assertEquals("Name", getResult.getName());
        verify(brand).getName();
        verify(brand).getId();
        verify(brand).setId(anyLong());
        verify(brand).setName(any());
    }


    @Test
    void shouldAsEntity() {
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        Brand actualAsEntityResult = brandMapperImpl.asEntity(brandDTO);
        assertEquals(1L, actualAsEntityResult.getId());
        assertEquals("Name", actualAsEntityResult.getName());
    }

    @Test
    void shouldAsEntity2() {
        BrandDTO brandDTO = mock(BrandDTO.class);
        when(brandDTO.getId()).thenReturn(1L);
        when(brandDTO.getName()).thenReturn("Name");
        doNothing().when(brandDTO).setId( any());
        doNothing().when(brandDTO).setName( any());
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        Brand actualAsEntityResult = brandMapperImpl.asEntity(brandDTO);
        assertEquals(1L, actualAsEntityResult.getId());
        assertEquals("Name", actualAsEntityResult.getName());
        verify(brandDTO, atLeast(1)).getId();
        verify(brandDTO).getName();
        verify(brandDTO).setId( any());
        verify(brandDTO).setName( any());
    }

    @Test
    void shouldAsEntity3() {
        BrandDTO brandDTO = mock(BrandDTO.class);
        when(brandDTO.getId()).thenReturn(null);
        when(brandDTO.getName()).thenReturn("Name");
        doNothing().when(brandDTO).setId( any());
        doNothing().when(brandDTO).setName( any());
        brandDTO.setId(1L);
        brandDTO.setName("Name");
        Brand actualAsEntityResult = brandMapperImpl.asEntity(brandDTO);
        assertEquals(0L, actualAsEntityResult.getId());
        assertEquals("Name", actualAsEntityResult.getName());
        verify(brandDTO).getId();
        verify(brandDTO).getName();
        verify(brandDTO).setId( any());
        verify(brandDTO).setName( any());
    }
}

