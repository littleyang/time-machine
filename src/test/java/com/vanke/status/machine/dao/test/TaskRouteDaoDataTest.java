package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.status.machine.dao.TaskRoutesDao;
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.model.TaskRoutes;
import com.vanke.status.machine.model.TaskStatus;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskRouteDaoDataTest extends BaseDaoTestBeans {
	
	@SpringBeanByType  
	private TaskRoutesDao taskRoutesDao;
	
	TaskRoutes route = new TaskRoutes();
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		route.setId(10000);
		route.setBussinessCode("test-business-code");
		route.setCurrentEvent("current-test-event");
		route.setCurrentStatus(10001);
		route.setNextEvent("next-test-event");
		route.setNextStatus(10002);
	}
	
	@After
	public void cleanAll(){
		System.out.println("======clean all test data ======");
		taskRoutesDao.deleteAllTaskRoutes();
	}
	
	
	@Test
	public void testGetAllTaskRoutesCount(){
		assertThat(taskRoutesDao.getAllRoutesCountByJdbc(), is(0));
	}
	
	@Test
	public void testCreateTaskStatus() throws BaseDaoException{
		
		TaskRoutes created = taskRoutesDao.create(route);
		assertThat("should be not null",created, is(notNullValue()));
		assertThat("should be not null",created.getId(), is(notNullValue()));
		assertThat("should be equal",route.getBussinessCode(), is(created.getBussinessCode()));
		
		taskRoutesDao.deleteTaskRoute(created);
		TaskRoutes deleted = taskRoutesDao.getTaskRouteById(created.getId());
		assertThat("should be null",deleted, is(nullValue()));
	}
	
//	@Test
//	public void testTaskStatusByJdbc() throws BaseDaoException{
//		
//		TaskStatus created = taskStatusDao.createTaskStatus(status);
//		
//		TaskStatus findOne = taskStatusDao.getTaskStatusByStatusByJdbc(created.getStatus());
//		
//		assertThat("should be not null",findOne, is(notNullValue()));
//		assertThat("should be equal",created.getId(), is(created.getId()));
//		assertThat("should be equal",created.getStatus(), is(created.getStatus()));
//		
//	}

}




