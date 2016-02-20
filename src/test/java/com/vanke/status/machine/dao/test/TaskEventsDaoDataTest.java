package com.vanke.status.machine.dao.test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.common.exceptions.BaseDaoException;
import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskEventsDaoDataTest extends BaseDaoTestBeans{
	
	@SpringBeanByType 
	private TaskEventsDao taskEventsDao;
	
	TaskEvents taskEvent = new TaskEvents();
	
	@Before
	public void setUp(){
		System.out.println("======set up all test data ======");
		taskEvent.setCode("E100100");
		taskEvent.setEvent("test-event");
		taskEvent.setName("test-create");
		taskEvent.setMsg("test 发布了任务");
		taskEvent.setType(0);
		taskEvent.setCreated(new Date());
		taskEvent.setUpdated(new Date());
	}
	
	@After
	public void clean(){
		System.out.println("======clean all test data ======");
		taskEventsDao.deleteAllEvents();
	}
	
	@Test
	//@DataSet("/data/task-events-test-data.xml")
	public void testGetAllEventsCount(){
		assertThat("all task events count should be 2",taskEventsDao.getAllEventsCountByJdbc()>0, is(true));
	}
	
	@Test
	public void testGetTaskEventByIdByCrudDao(){
		TaskEvents taskEvent = taskEventsDao.getTaskByCrudDaoById(1);
		assertThat("task event id should be equal",taskEvent.getId(), is(1));
		assertThat("task event name should be equal init ",taskEvent.getEvent(), is("create"));
	}
	
	@Test
	public void testGetTaskEventByCodeByCrudDao(){
		TaskEvents taskEvent = taskEventsDao.getTaskByCrudDaoByCode("E100001");
		assertThat("task event id should be equal",taskEvent.getId(), is(1));
		assertThat("task event name should be equal init ",taskEvent.getEvent(), is("create"));
	}
	
	@Test
	public void testCreatTaskEventByCrudDao(){
		
		TaskEvents taskEventTemp = taskEventsDao.createTaskEvent(taskEvent);
		assertThat("task event code should be equal ",taskEvent.getCode(), is(taskEventTemp.getCode()));
		assertThat("task event name should be equal ",taskEvent.getName(), is(taskEventTemp.getName()));
	}
	
	@Test
	public void testDeleteTaskEventByCrudDao(){
		
		TaskEvents taskEventTemp = taskEventsDao.createTaskEvent(taskEvent);
		taskEventsDao.deleteTaskEvents(taskEventTemp);
		TaskEvents deletedEvent = taskEventsDao.getTaskByCrudDaoByCode(taskEvent.getCode());
		assertNull(deletedEvent);
		assertThat("task event code should be equal ",deletedEvent, is(nullValue()));
	}
	
	
	@Test
	public void testGetAllTaskEventsByJdbc(){
		List<TaskEvents> list = taskEventsDao.getAllTaskStatusByJdbc();
		assertThat("should be null", list, is(notNullValue()));
		assertThat("should be equal", list.size()>0, is(true));
	}
	
	
	@Test
	public void testGetAllTaskEventsByCrud(){
		List<TaskEvents> list = taskEventsDao.findAllByCrudDao();
		assertThat("should be null", list, is(notNullValue()));
		assertThat("should be equal", list.size()>0, is(true));
	}
	
	@Test(expected=BaseDaoException.class)
	public void testGetTaskEventsByCodeByJdbc() throws BaseDaoException{
		String code = "";
		TaskEvents event = taskEventsDao.getTaskEventByCodeByJdbc(code);
		assertThat("task event code should be null ",event, is(nullValue()));
	}
	
	@Test
	public void testGetTaskEventsByCodeByJdbcNoException() throws BaseDaoException{
		String code = "E100001";
		TaskEvents event = taskEventsDao.getTaskEventByCodeByJdbc(code);
		assertThat("task event code should be null ",event, is(notNullValue()));
		assertThat("task event code should be null ",code, is(event.getCode()));
	}
	
}
