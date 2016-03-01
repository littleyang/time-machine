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
import com.vanke.status.machine.controller.ZhuzherTaskController;
import com.vanke.status.machine.service.TaskStatusMachineService;
import com.vanke.test.base.BaseTestUnit;
import com.vanke.test.base.TestUtil;


public class ZhzherTaskControllerUnitTest extends BaseTestUnit{
	
	private MockMvc mockMvc;
	
	@Mock
	private TaskService taskService;
	
	@Mock
	private TaskStatusMachineService taskStatusMachineService;
	
	@InjectMocks
	private ZhuzherTaskController zhuzherTaskController;
	
	
	@Before
	public void setUp(){
		System.out.println("init test ");
		this.mockMvc = MockMvcBuilders.standaloneSetup(zhuzherTaskController).build();
	}
	
	@Test
	public void testZhuzherTaskControllerCreateTask() throws Exception{
		
//		Task task = new Task();
//		task.setBusinessType("BUCR020103");
		
		String taskJson = "{\"bussiness_type\":\"\"}";
		
		mockMvc.perform(post("/api/zhuzher/task/create")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(taskJson))
				.andExpect(status().isOk());
	}

}
