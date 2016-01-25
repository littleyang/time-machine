package com.vanke.status.machine.dao.test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.Date;

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
	
	@Test
	public void testCreatTaskEventByCrudDao(){
		TaskEvents taskEvent = new TaskEvents();
		taskEvent.setCode("E10010");
		taskEvent.setName("test-create");
		taskEvent.setMsg("test 发布了任务");
		taskEvent.setType(0);
		taskEvent.setCreated(new Date());
		taskEvent.setUpdated(new Date());
		TaskEvents taskEventTemp = taskEventsDao.createTaskEvent(taskEvent);
		assertThat("task event code should be equal ",taskEvent.getCode(), is(taskEventTemp.getCode()));
		assertThat("task event name should be equal ",taskEvent.getName(), is(taskEventTemp.getName()));
	}
	
	@Test
	public void testDeleteTaskEventByCrudDao(){
		TaskEvents taskEvent = new TaskEvents();
		taskEvent.setCode("E10010");
		taskEvent.setName("test-create");
		taskEvent.setMsg("test 发布了任务");
		taskEvent.setType(0);
		taskEvent.setCreated(new Date());
		taskEvent.setUpdated(new Date());
		TaskEvents taskEventTemp = taskEventsDao.createTaskEvent(taskEvent);
		taskEventsDao.deleteTaskEvents(taskEventTemp);
		TaskEvents deletedEvent = taskEventsDao.getTaskByCrudDaoByCode(taskEvent.getCode());
		assertNull(deletedEvent);
		assertThat("task event code should be equal ",deletedEvent, is(nullValue()));
	}
}
