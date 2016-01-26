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
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.model.TaskStatus;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskRouteDaoDataTest extends BaseDaoTestBeans {
	
	@SpringBeanByType  
	private TaskStatusDao taskStatusDao;
	
	TaskStatus status = new TaskStatus();
	
	@Test
	public void testGetAllStatusCount(){
		System.out.println(taskStatusDao.getAllTaskStatusCountByJdbc());
	}
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		status.setStatus(1005);
		status.setName("test-status");
		status.setOuterStatus(10000);
		status.setOuterName("test-out-name");
		status.setType(0);
		status.setCreated(new Date());
		status.setUpdated(new Date());
	}
	
	@After
	public void cleanAll(){
		System.out.println("======clean all test data ======");
		//taskStatusDao.deleteAllTaskStatus();
	}
	
	
	@Test
	public void testCreateTaskStatus(){
		
		TaskStatus created = taskStatusDao.createTaskStatus(status);
		
		assertThat("should be not null",created, is(notNullValue()));
		assertThat("should be not null",created.getId(), is(notNullValue()));
		assertThat("should be equal",status.getStatus(), is(created.getStatus()));
		
		taskStatusDao.deleteTaskStatus(created);
		
		TaskStatus deleted = taskStatusDao.findOneByStatus(status.getStatus());
		assertThat("should be null",deleted, is(nullValue()));
	}
	
	@Test
	public void testTaskStatusByJdbc() throws BaseDaoException{
		
		TaskStatus created = taskStatusDao.createTaskStatus(status);
		
		TaskStatus findOne = taskStatusDao.getTaskStatusByStatusByJdbc(created.getStatus());
		
		assertThat("should be not null",findOne, is(notNullValue()));
		assertThat("should be equal",created.getId(), is(created.getId()));
		assertThat("should be equal",created.getStatus(), is(created.getStatus()));
		
	}

}




