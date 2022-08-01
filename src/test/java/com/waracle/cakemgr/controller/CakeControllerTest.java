package com.waracle.cakemgr.controller;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.waracle.cakemgr.entity.CakeEntity;
import com.waracle.cakemgr.service.CakeService;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CakeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CakeService cakeService;
	
	private CakeEntity cakeEntity;
	

	@BeforeEach
	void setup() {
		cakeEntity = new CakeEntity("test1", "test2", "test3");
		cakeEntity.setCakeId(1l);
	}
	
	@Test
	void whenRootIsCalledThenDataIsRetrievedAndStringIsReturned() throws Exception {
		doNothing().when(cakeService).retrieveCakeDataFromServer();
		mockMvc.perform(get("/")).andExpect(status().isOk());

		verify(cakeService).retrieveCakeDataFromServer();

		verifyNoMoreInteractions(cakeService);
	}
	
	@Test
	void whenGetCakesIsCalledThenDataIsRetrievedAndStringIsReturned() throws Exception {
		List<CakeEntity> cakes = Arrays.asList(cakeEntity);
		when(cakeService.getAllCakes()).thenReturn(cakes);
		
		mockMvc.perform(get("/cakes"))
		.andExpect(status()
				.isOk())
		.andExpect(content().string(containsString("test")));
		verify(cakeService).getAllCakes();

		verifyNoMoreInteractions(cakeService);
	}
	
	@Test
	void whenPostCakesIsCalledThenDataIsRetrievedAndStringIsReturned() throws Exception {
		
		when(cakeService.save(Mockito.any())).thenReturn(cakeEntity);
		
		mockMvc.perform(post("/cakes").flashAttr("cake", new CakeEntity("test1", "test2", "test3")))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("test")));
		
		verify(cakeService).save(Mockito.any());

		verifyNoMoreInteractions(cakeService);
	}
	
	@Test
	void whenPutCakesIsCalledThenDataIsRetrievedAndStringIsReturned() throws Exception {
		
		when(cakeService.save(Mockito.any())).thenReturn(cakeEntity);
		
		mockMvc.perform(put("/cakes").flashAttr("cake", new CakeEntity("test21", "test22", "test33")))
		.andExpect(status().isOk())
		.andExpect(content().string(containsString("test")));
		verify(cakeService).save(Mockito.any());

		verifyNoMoreInteractions(cakeService);
	}
}
