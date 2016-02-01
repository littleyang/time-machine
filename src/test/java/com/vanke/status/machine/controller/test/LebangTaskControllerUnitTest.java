package com.vanke.status.machine.controller.test;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.vanke.common.model.task.Task;
import com.vanke.common.service.TaskService;
import com.vanke.status.machine.controller.LebangTaskController;
import com.vanke.status.machine.service.TaskStatusMachineService;
import com.vanke.test.base.BaseTestUnit;
import com.vanke.test.base.TestUtil;


public class LebangTaskControllerUnitTest extends BaseTestUnit{
	
	private MockMvc mockMvc;
	
	@Mock
	private TaskService taskService;
	
	@Mock
	private TaskStatusMachineService taskStatusMachineService;
	
	@InjectMocks
	private LebangTaskController lebangTaskController;
	
	
	@Before
	public void setUp(){
		System.out.println("init test ");
		this.mockMvc = MockMvcBuilders.standaloneSetup(lebangTaskController).build();
	}
	
	@Test
	public void testLebangTaskControllerCreateTask() throws Exception{
		
		Task task = new Task();
		task.setBusinessType("BUCR020103");
		
		mockMvc.perform(post("/api/lebang/task/create")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(task)))
				.andExpect(status().isOk());
	}

}
