package com.vanke.status.machine.controller.test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.model.task.Task;
import com.vanke.common.service.TaskService;
import com.vanke.status.machine.controller.LebangTaskController;
import com.vanke.status.machine.service.TaskStatusMachineService;
import com.vanke.test.base.BaseDaoTestBeans;
import com.vanke.test.base.TestUtil;


public class LebangTaskControllerDataTest extends BaseDaoTestBeans{
	
	private MockMvc mockMvc;
	
	@SpringBeanByType
	private TaskService taskService;
	
	@SpringBeanByType
	private TaskStatusMachineService taskStatusMachineService;
	
	@SpringBeanByType
	private LebangTaskController lebangTaskController;
	
	
	@Before
	public void setUp(){
		System.out.println("init test ");
		this.mockMvc = MockMvcBuilders.standaloneSetup(lebangTaskController).build();
	}
	
	@After
	public void clean(){
		System.out.println("======clean all test data ======");
		taskService.deleteAllTask();;
	}
	
	
	@Test
	public void testLebangTaskControllerCreateTask() throws Exception{
		
		Task task = new Task();
		task.setBusinessType("BUCR020103");
		
		mockMvc.perform(post("/api/lebang/task/create")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(task))).andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.task", is(notNullValue())))
				.andExpect(jsonPath("$.task.business_type", is("BUCR020103")))
				.andExpect(jsonPath("$.task.status", is(1001)))
				.andExpect(jsonPath("$.operations", hasSize(1)))
				.andExpect(jsonPath("$.operations[0]", is(notNullValue())))
				.andExpect(jsonPath("$.operations[0].code", is("E100002")))
				.andExpect(jsonPath("$.operations[0].type", is(100)));
	}

}
