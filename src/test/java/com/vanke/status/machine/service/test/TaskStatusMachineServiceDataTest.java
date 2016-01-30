package com.vanke.status.machine.service.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.data.vo.TaskSnapshot;
import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.exceptions.BaseServiceException;
import com.vanke.common.model.task.Task;
import com.vanke.common.task.dao.TaskDao;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.status.machine.service.TaskStatusMachineService;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskStatusMachineServiceDataTest extends BaseDaoTestBeans{
	
	@SpringBeanByType 
	private TaskDao taskDao;
	
	@SpringBeanByType
	private TaskStatusMachineService taskStatusMachineService;
	
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		
	}
	
	@After
	public void clean(){
		System.out.println("======clean all test data ======");
		taskDao.deleteAllTask();;
	}
	
	
	@Test
	public void testCreateTaskRouteShouldGetNextTaskStatusAndEvents() throws BaseServiceException, BaseDaoException{
		
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		task.setStatus(1000);
		
		String currentEvent = "E100001";
		
		Task createTemp = taskDao.createTask(task);
	
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent);
		
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
		
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1001));
		
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(1));
		
		TaskEvents nextEventTemp = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTemp, is(notNullValue()));
		assertThat("task should be not null ",nextEventTemp.getCode(), is("E100002"));
	}
	
	
	@Test
	public void testPublishTaskRoutesShouldGetAndFindRightNextRoutes() throws BaseDaoException, BaseServiceException{
		
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		task.setStatus(1001);
		
		String currentEvent = "E100002";
		
		Task createTemp = taskDao.createTask(task);
	
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent);
		
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
		
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1004));
		
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(1));
		
		TaskEvents nextEventTemp = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTemp, is(notNullValue()));
		assertThat("task should be not null ",nextEventTemp.getCode(), is("E100003"));
	}
	

	

}
