package com.vanke.status.machine.service.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.constant.ResponesCodeConst;
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
		//taskDao.deleteAllTask();;
	}
	
	
	@Test
	public void testCreateTaskRouteShouldGetNextTaskStatusAndEventsIsPublishGrabingTask() throws BaseServiceException, BaseDaoException{
		
		//创建Task测试，下一条路由应该是发布抢单，任务状态应该是变成等待员工抢单状态
		
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前task应该是初始化状态
		task.setStatus(1000);
		
		String currentEvent = "E100001";
		
		Task createTemp = taskDao.createTask(task);
	
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
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
	public void testPublishTaskRoutesShouldGetAndFindRightNextRoutesIsGradTask() throws BaseDaoException, BaseServiceException{
		
		// 发布抢单测试，下一条路由应该是接受任务，任务变成等待员工抢单状态
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前工单状态应该是发起任务状态
		task.setStatus(1001);
		
		// 发布抢单操作
		String currentEvent = "E100002";
		
		Task createTemp = taskDao.createTask(task);
	
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
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
	
	@Test
	public void testAcceptTaskShouldGetNextRoutesIsBeginTaskAndCannotExcuteTask() throws BaseDaoException, BaseServiceException{
		// 员工抢单测试，抢单之后的路由应该是接受任务或者是无法处理，任务状态变成已经接受已经接受状态：1014
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是等待抢单状态
		task.setStatus(1004);
		
		//抢单操作
		String currentEvent = "E100003";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);

		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
		
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1014));
		
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(2));
		
		// 下一步可进行的第1个操作是接受任务
		TaskEvents nextEventTempOne = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTempOne, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempOne.getCode(), is("E100010"));
		
		// 下一步可进行的第2个操作是无法处理
		TaskEvents nextEventTempTwo = operationsTemp.get(1);
		assertThat("task should be not null ",nextEventTempTwo, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempTwo.getCode(), is("E100014"));
		
	}
	

}
