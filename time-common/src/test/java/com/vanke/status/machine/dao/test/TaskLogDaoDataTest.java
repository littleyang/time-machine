package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.common.model.task.TaskLog;
import com.vanke.common.task.dao.TaskLogDao;
import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.model.TaskStatus;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskLogDaoDataTest extends BaseDaoTestBeans {
	
	@SpringBeanByType  
	private TaskLogDao taskLogDao;
	
	TaskLog log = new TaskLog();
	
	@Test
	public void testGetAllStatusCount(){
		System.out.println(taskLogDao.countAllTaskLogByJdbc());
	}
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		log.setObjectId(123);
		log.setEvent("test");
		log.setCreated(new Date());
		log.setMsg("test");
		log.setRate(1);
		log.setStatus(1000);
		log.setSourceId(10000);
		log.setScore("6");
		log.setTaskNo("yyyyyyyssssss");
	}
	
	@After
	public void cleanAll(){
		System.out.println("======clean all test data ======");
		taskLogDao.trucateAllTaskLogs();
	}
	
	
	@Test
	public void testCreateTaskStatus() throws BaseDaoException{
		
		TaskLog created = taskLogDao.createTaskLog(log);
		
		assertThat("should be not null",created, is(notNullValue()));
		assertThat("should be not null",created.getId(), is(notNullValue()));
		assertThat("should be equal",log.getStatus(), is(created.getStatus()));
		
		taskLogDao.deleteTaskLog(created);
		
		TaskLog deleted = taskLogDao.findByTaskId(created.getId());
		assertThat("should be null",deleted, is(nullValue()));
	}
	
}




