package com.vanke.status.machine.cache.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
	
//	@BeforeClass
//	public static void setUp(){
//		System.out.println("====setup all test data=====");
//		event = new TaskEvents();
//		event.setId(10000);
//		event.setCode("E100000");
//		event.setName("test-task-event");
//		event.setType(1);
//		event.setMsg("test-task-event-msg");
//		event.setCreated(new Date());
//		event.setUpdated(new Date());
//		eventKey = "task_events";
//	}
	
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
	
//	@AfterClass
//	public static void clean(){
//		System.out.println("====clean all test data=====");
//		taskEventMongoCacheManager.getMongoTemplate().dropCollection(eventKey);
//	}
	
	@Test
	public void testCreateTaskEventsToMongo(){
				
		taskEventMongoCacheManager.insertValue(event, eventKey);
	
	}
	
	
	@Test
	public void testGetTaskEventsByIdFromMongo(){
		
		taskEventMongoCacheManager.insertValue(event, eventKey);
		
		TaskEvents eventTemp = (TaskEvents) taskEventMongoCacheManager.findOneById(event.getId(), eventKey);
		
		assertThat("task event id should be equal",event.getId(), is(eventTemp.getId()));
		
	}
	
	@Test
	public void testGetTaskEventsByCodeFromMongo(){
		
		taskEventMongoCacheManager.insertValue(event, eventKey);
		
		TaskEvents eventTemp = (TaskEvents) taskEventMongoCacheManager.findOneByCode(event.getCode(), eventKey);
		
		assertThat("task event id should be equal",eventTemp.getCode(), is(eventTemp.getCode()));
		
	}

}
