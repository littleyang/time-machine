package com.vanke.status.machine.dao.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskEventsDaoDataTest extends BaseDaoTestBeans{
	
	@SpringBeanByType 
	private TaskEventsDao taskEventsDao;
	
	@Test
	//@DataSet("/data/task-events-test-data.xml")
	public void testGetAllEventsCount(){
		assertThat("all task events count should be 2",taskEventsDao.getAllEventsCountByJdbc(), is(2));
	}
	
	@Test
	public void testGetTaskEventByIdByCrudDao(){
		TaskEvents taskEvent = taskEventsDao.getTaskByCrudDaoById(1);
		assertThat("task event id should be equal",taskEvent.getId(), is(1));
		assertThat("task event name should be equal init ",taskEvent.getName(), is("init"));
	}
	
	@Test
	public void testGetTaskEventByCodeByCrudDao(){
		TaskEvents taskEvent = taskEventsDao.getTaskByCrudDaoByCode("E1001");
		assertThat("task event id should be equal",taskEvent.getId(), is(1));
		assertThat("task event name should be equal init ",taskEvent.getName(), is("init"));
	}


}
