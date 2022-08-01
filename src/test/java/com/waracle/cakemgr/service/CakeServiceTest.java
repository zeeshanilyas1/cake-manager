package com.waracle.cakemgr.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.properties.CakeManagerProperties;
import com.waracle.cakemgr.service.repositories.CakeEntityRepository;

@ExtendWith(MockitoExtension.class)
class CakeServiceTest {
	
	private final static String ENDPOINT = "https://localhost:8080/test";

	
	@InjectMocks
	CakeService cakeService;
	
	@Mock
	CakeEntityRepository cakeEntityRepository;	
	
	@Mock
	CakeManagerProperties properties;
	
	@Mock
	CakeEntity cakeEntity;
	
	@Mock
	List<CakeEntity> listofCakeEntity;
	
	@Mock
	InputStream inputStream;
	
	@Mock
	BufferedReader bufferedReader;
	
	@BeforeEach
	void setUp() throws Exception {

		lenient().when(properties.getEndpoint()).thenReturn(ENDPOINT);
		
		lenient().when(cakeEntityRepository.save(Mockito.any())).thenReturn(cakeEntity);
		lenient().when(cakeEntityRepository.findAll()).thenReturn(listofCakeEntity);

		lenient().when(cakeEntityRepository.getById(Mockito.anyLong())).thenReturn(cakeEntity);

	}
	
	@Test
    void whenGetAllCakesIsCalledThenAllDataIsRetrieved() {
	
		List<CakeEntity> cakes = cakeService.getAllCakes();
		assertEquals(listofCakeEntity, cakes);
		verify(cakeEntityRepository).findAll();
		verifyNoMoreInteractions(cakeEntityRepository);

	}

	@Test
    void whenGetAllCakesIsCalledAnNoDataThenAllDataReturnsNull() {
	
		when(cakeEntityRepository.findAll()).thenReturn(null);
		
		List<CakeEntity> cakes = cakeService.getAllCakes();
		assertEquals(null, cakes);
		verify(cakeEntityRepository).findAll();
		verifyNoMoreInteractions(cakeEntityRepository);

	}
	
	@Test
    void whenGetCakeIsCalledThenDataIsRetrieved() {
	
		CakeEntity cake = cakeService.getCake(Long.valueOf(1l));
		assertEquals(cakeEntity, cake);
		verify(cakeEntityRepository).getById(Mockito.anyLong());
		verifyNoMoreInteractions(cakeEntityRepository);

	}

	@Test
    void whenGetCakesIsCalledAnNoDataExistsThenNoDataIsReturned() {
	
		when(cakeEntityRepository.getById(Mockito.anyLong())).thenReturn(null);
		
		CakeEntity cake = cakeService.getCake(Mockito.anyLong());

		assertEquals(null, cake);
		verify(cakeEntityRepository).getById(Mockito.anyLong());
		verifyNoMoreInteractions(cakeEntityRepository);

	}
	
	@Test
    void whenDeleteIsCalledThenDataIsDeleted() {

		Long id = Long.valueOf(1l);
		
		cakeService.delete(id);
		
		verify(cakeEntityRepository).deleteById(id);
		verifyNoMoreInteractions(cakeEntityRepository);

	}
	
}
