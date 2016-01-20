package com.vanke.status.machine.controller.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.vanke.status.machine.controller.StatusRouteController;
import com.vanke.status.machine.service.StatusRouteService;
import com.vanke.test.base.BaseTest;


public class StatusRouteControllerFindNextStatusTest extends BaseTest{
	
	private MockMvc mockMvc;
	
	@Mock
	private StatusRouteService routeService;
	
	@InjectMocks
	private StatusRouteController routeController;
	
	
	@Before
	public void setUp(){
		this.mockMvc = MockMvcBuilders.standaloneSetup(routeController).build();
	}
	
	@Test
	public void shouldReturnZeroWhenAllParamsPassed() throws Exception{
		
		when(routeService.getRouteNextStatusByTypeLastStatusEvent("hello", 1000, "test")).thenReturn(0);
		
		mockMvc.perform(post("/task/machine/status/next").param("business_type", "hello")
				.param("current_status", "1000")
				.param("event", "create")).andDo(print())
				.andExpect(status().isOk());
		
		verify(routeService).getRouteNextStatusByTypeLastStatusEvent("hello", 1000, "test");
		
	}

}
