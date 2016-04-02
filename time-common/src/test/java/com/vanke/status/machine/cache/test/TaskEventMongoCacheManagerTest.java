package com.vanke.status.machine.cache.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.status.machine.cache.TaskEventMongoCacheManager;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.test.base.BaseTestUnit;

public class TaskEventMongoCacheManagerTest extends BaseTestUnit{
	
	@Autowired
	private TaskEventMongoCacheManager taskEventMongoCacheManager;
	
	private TaskEvents event;
	
	private String eventKey;
	
	
	@Before
	public void setEachTest(){
		System.out.println("\n======setup each test case=======\n");
		event = new TaskEvents();
		event.setId(10000);
		event.setCode("E100000");
		event.setName("test-task-event");
		event.setType(1);
		event.setMsg("test-task-event-msg");
		event.setCreated(new Date());
		event.setUpdated(new Date());
		eventKey = "task_events";
	}
	
	@After
	public void cleanEachTest(){
		System.out.println("\n====clean each test data=====\n");
		taskEventMongoCacheManager.getMongoTemplate().dropCollection(eventKey);
	}
	

	@Test
	public void testCreateTaskEventsToMongo(){		
		taskEventMongoCacheManager.create(event);
	}
	
	
	@Test
	public void testGetTaskEventsByIdFromMongo(){
		
		taskEventMongoCacheManager.create(event);
		
		TaskEvents eventTemp = (TaskEvents) taskEventMongoCacheManager.findOneById(event.getId());
		
		assertThat("task event id should be equal",event.getId(), is(eventTemp.getId()));
		
	}
	
	@Test
	public void testGetTaskEventsByCodeFromMongo(){
		
		taskEventMongoCacheManager.create(event);
		
		TaskEvents eventTemp = (TaskEvents) taskEventMongoCacheManager.findOneByCode(event.getCode());
		
		assertThat("task event id should be equal",eventTemp.getCode(), is(eventTemp.getCode()));
		
	}
	
	@Test
	public void testUpdateTaskEvents(){
		
		taskEventMongoCacheManager.create(event);
		
		TaskEvents eventTemp = (TaskEvents) taskEventMongoCacheManager.findOneByCode(event.getCode());
		
		eventTemp.setMsg("this is update msssssss");
		
		taskEventMongoCacheManager.updateOne(eventTemp);
		
		TaskEvents eventTempAfterUpdate = (TaskEvents) taskEventMongoCacheManager.findOneByCode(event.getCode());
		
		assertThat("task event id should be equal",eventTemp.getCode(), is(eventTempAfterUpdate.getCode()));
		assertThat("task event id should be equal",eventTemp.getMsg(), is(eventTempAfterUpdate.getMsg()));
		
	}

}
