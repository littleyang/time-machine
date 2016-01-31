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
	
	@Test
	public void testAcceptTaskShouldGetNextRoutesIsBegainTask() throws BaseServiceException, BaseDaoException{
		
		// 测试接受Task，路由应该是接受，变化之后的状态应该是开始工作：1006
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是任务已接受状态
		task.setStatus(1014);
		
		// 接受任务操作
		String currentEvent = "E100010";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
		
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1006));
		
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(1));
		
		// 下一步可进行的第1个操作是接受任务
		TaskEvents nextEventTempOne = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTempOne, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempOne.getCode(), is("E100004"));
		
	}
	
	@Test
	public void testCannotAcceptTaskShouldGetNextRoutesIsPublishGrabTask() throws BaseServiceException, BaseDaoException{
		
		// 测试无法处理Task，路由应该是无法处理，变化之后的状态应该是关闭状态：1010,下一个操作应该是是重新发布抢单
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是任务已接受状态
		task.setStatus(1014);
		
		// 无法处理任务操作
		String currentEvent = "E100014";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
		
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1010));
		
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(1));
		
		// 下一步可进行的有且只有一个操作，是重新发布抢单操作
		TaskEvents nextEventTempOne = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTempOne, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempOne.getCode(), is("E100001"));
		
	}
	
	@Test
	public void testStartTaskShouldBeGetNextRoutesIsFinishTaskAndPauseTask() throws BaseServiceException, BaseDaoException{
		
		// 测试开始处理Task，路由应该是开始处理，变化之后的状态应该是任务处理中：1007.
		// 下一步操作应该是两个操作:完成工作，暂停处理
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是已经接受状态
		task.setStatus(1006);
		
		// 开始处理任务操作
		String currentEvent = "E100004";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
		// 路由之后的结果应该是正常的
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
		
		// 处理之后的任务状态是1007，任务处理中状态
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1007));
		
		// 下一步的操作应该是两个:完成工作，暂停工作两个操作
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(2));
		
		// 下一步的操作分别是:完成工作，暂停工作两个操作.完成工作
		TaskEvents nextEventTempOne = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTempOne, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempOne.getCode(), is("E100007"));
		
		// 下一步的操作分别是:完成工作，暂停工作两个操作.暂停工作
		TaskEvents nextEventTempTwo = operationsTemp.get(1);
		assertThat("task should be not null ",nextEventTempTwo, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempTwo.getCode(), is("E100005"));
		
	}
	
	@Test
	public void pauseTaskShouldGetNextRouteIsContinueTask() throws BaseServiceException, BaseDaoException{
		// 测试暂停工作，初始的工单状态是：1007，处理中，操作之后的工单状态是：1008，暂停任务
		// 对应的下一步操作是:继续任务
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是：处理中
		task.setStatus(1007);
		
		// 暂停处理任务操作
		String currentEvent = "E100005";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
		// 路由之后的结果应该是正常的
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
						
		// 处理之后的任务状态是1008，完成
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1008));
		
		// 下一步的操作应该是一个:
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(1));
		
		// 下一步的操作分别是:继续工作
		TaskEvents nextEventTempOne = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTempOne, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempOne.getCode(), is("E100006"));
		
		// 下一步的操作分别是:完成工作，暂停工作两个操作.暂停工作
		TaskEvents nextEventTempTwo = operationsTemp.get(1);
		assertThat("task should be not null ",nextEventTempTwo, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempTwo.getCode(), is("E100005"));
		
	}
	
	@Test
	public void continueTaskShouldGetNextRouteIsFinishTask() throws BaseServiceException, BaseDaoException{
		// 暂停状态下继续工作，初始状态是：1008，暂停，处理之后的状态应该是：1007，处理中
		// 下一步操作是完成工作
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是：处理中
		task.setStatus(1008);
		
		// 暂停处理任务操作
		String currentEvent = "E100006";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);
		
		// 路由之后的结果应该是正常的
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
								
		// 下一步的操作应该是两个:完成工作，暂停工作两个操作
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(2));
				
		// 下一步的操作分别是:完成工作，暂停工作两个操作.完成工作
		TaskEvents nextEventTempOne = operationsTemp.get(0);
		assertThat("task should be not null ",nextEventTempOne, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempOne.getCode(), is("E100007"));
				
		// 下一步的操作分别是:完成工作，暂停工作两个操作.暂停工作
		TaskEvents nextEventTempTwo = operationsTemp.get(1);
		assertThat("task should be not null ",nextEventTempTwo, is(notNullValue()));
		assertThat("task should be not null ",nextEventTempTwo.getCode(), is("E100005"));
		
	}
	
	@Test
	public void testFinishTaskShouldGetNextRoutesIsEvalutionRoutes() throws BaseServiceException, BaseDaoException{
		
		// 测试测试完成工单，完成之前的状态是正在处理中状态：1007，操作之后的状态是完成带评价状态：1009
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是：工单处理中
		task.setStatus(1007);
		
		// 完成任务操作
		String currentEvent = "E100007";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_LEBANG);

		// 路由之后的结果应该是正常的
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
				
		// 处理之后的任务状态是1009，完成
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1009));
		
		// 员工再完成之后无法再操作了，也就没有对应的操作事件
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(0));
				
	}
	
	@Test
	public void testUserEvalutionTask() throws BaseServiceException, BaseDaoException{
		// 测试用户评价任务，初始状态是：完成待评价，操作之后的状态是：已评价状态
		Task task = new Task();
		task.setTaskNo("19111010010002");
		task.setBusinessType("BUCR020103");
		
		// 操作之前的任务状态应该是：已经完成带评价
		task.setStatus(1009);
		
		// 完成任务操作
		String currentEvent = "E100008";
		
		Task createTemp = taskDao.createTask(task);
		
		TaskSnapshot result = taskStatusMachineService.operationTask(createTemp,currentEvent,ResponesCodeConst.TASK_EVENT_TYPE_ZHUZHER);

		// 路由之后的结果应该是正常的
		assertThat("task should be not null ",createTemp, is(notNullValue()));
		assertThat("task snapshot should be not null ",result, is(notNullValue()));
				
		// 处理之后的任务状态是1011，已经评价
		Task resultTaskTemp = result.getTask();
		assertThat("task should be not null ",resultTaskTemp, is(notNullValue()));
		assertThat("task should be not null ",resultTaskTemp.getStatus(), is(1011));
		
		// 员工再完成之后无法再操作了，也就没有对应的操作事件
		List<TaskEvents> operationsTemp = result.getOperations();
		assertThat("task should be not null ",operationsTemp, is(notNullValue()));
		assertThat("task should be not null ",operationsTemp.size(), is(0));
		
	}
	
}
