package com.vanke.status.machine.service.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.exceptions.BaseServiceException;
import com.vanke.common.model.task.Task;
import com.vanke.common.task.dao.TaskCrudDao;
import com.vanke.common.task.dao.TaskDao;
import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.status.machine.dao.TaskRoutesDao;
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.service.TaskStatusMachineService;
import com.vanke.test.base.BaseTestUnit;

public class TaskStatusMachineServiceUnitTest extends BaseTestUnit{
	
	@Mock
	private TaskCrudDao taskCrudDao;
	
	@Mock
	private TaskRoutesDao taskRoutesDao;
	
	@Mock
	private TaskEventsDao taskEventsDao;
	
	@Mock
	private TaskStatusDao taskStatusDao;
	
	@InjectMocks
	private TaskDao taskDao;
	
	@InjectMocks
	private TaskStatusMachineService taskStatusMachineService;
	
	Task task = new Task();
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
//		task.setTaskNo("1911101001");
//		task.setBusinessType("BUCR020103");
//		task.setStatus(1000);
	}
	
	@After
	public void clean(){
		System.out.println("======clean all test data ======");
		//taskDao.deleteAllTask();;
	}
	
	
	@Test(expected=BaseServiceException.class)
	public void testShouldBeExceptionIfTaskIsNull() throws BaseServiceException, BaseDaoException{
		taskStatusMachineService.operationTask(null,"test",100);
	}
	
	@Test(expected=BaseServiceException.class)
	public void testShouldBeExceptionIfOperationIsNull() throws BaseServiceException, BaseDaoException{
		taskStatusMachineService.operationTask(task, null,100);
	}
	
	@Test(expected=BaseServiceException.class)
	public void testShouldBeExceptionIfOperationAndTaskIsNull() throws BaseServiceException, BaseDaoException{
		taskStatusMachineService.operationTask(null, null,100);
	}
	
	@Test(expected=BaseServiceException.class)
	public void testShouldBeExceptionIfTaskBussinessCodeIsNull() throws BaseServiceException, BaseDaoException{
		taskStatusMachineService.operationTask(task, "test-test",100);
	}
	

}
