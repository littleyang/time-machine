package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.status.machine.dao.TaskRoutesDao;
import com.vanke.status.machine.model.TaskRoutes;
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
		assertThat(taskRoutesDao.getAllRoutesCountByJdbc()>0, is(true));
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
	
	@Test
	public void testTaskStatusByJdbc() throws BaseDaoException{
		
		TaskRoutes created = taskRoutesDao.create(route);
		TaskRoutes findOne = taskRoutesDao.getTaskRoutesByIdByJdbc(created.getId());
		
		assertThat("should be not null",findOne, is(notNullValue()));
		assertThat("should be equal",created.getId(), is(findOne.getId()));
		assertThat("should be equal",created.getBussinessCode(), is(findOne.getBussinessCode()));
		
	}
	
	@Test
	public void testGetAllTaskRoutes(){
		List<TaskRoutes> list = taskRoutesDao.findAllByCrudDao();
		assertThat("should be null", list, is(notNullValue()));
		assertThat("should be equal", list.size()>0, is(true));
	}
	
	@Test
	public void testGetAllTaskRoutesByJdbc() throws BaseDaoException{
		List<TaskRoutes> list = taskRoutesDao.getAllTaskRoutesByJdbc();
		assertThat("should be null", list, is(notNullValue()));
		assertThat("should be equal", list.size()>0, is(true));
	}

}




