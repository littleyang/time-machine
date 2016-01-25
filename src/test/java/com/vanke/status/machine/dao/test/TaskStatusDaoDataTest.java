package com.vanke.status.machine.dao.test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.status.machine.model.TaskStatus;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskStatusDaoDataTest extends BaseDaoTestBeans {
	
	@SpringBeanByType  
	private TaskStatusDao taskStatusDao;
	
	@Test
	public void testGetAllStatusCount(){
		System.out.println(taskStatusDao.getAllTaskStatusCountByJdbc());
	}
	
	@Test
	public void testCreateTaskStatus(){
		TaskStatus status = new TaskStatus();
		//status.setId(10000);
		status.setStatus(1005);
		status.setName("test-status");
		status.setOuterStatus(10000);
		status.setOuterName("test-out-name");
		status.setType(0);
		status.setCreated(new Date());
		status.setUpdated(new Date());
		
		TaskStatus created = taskStatusDao.createTaskStatus(status);
		
		assertThat("should be not null",created, is(notNullValue()));
		assertThat("should be not null",created.getId(), is(notNullValue()));
		assertThat("should be equal",status.getStatus(), is(created.getStatus()));
	}

}




