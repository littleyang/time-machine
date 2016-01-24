package com.vanke.status.machine.cache.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.status.machine.cache.TaskEventRedisCacheManager;
import com.vanke.status.machine.model.TaskEvents;
import com.vanke.test.base.BaseTestUnit;

public class TaskEventRedisCacheManagerTest extends BaseTestUnit{
	
	@Autowired
	private TaskEventRedisCacheManager taskEventRedisCacheManager;
	
	private TaskEvents event;
	
	private String eventKey;
	
	@Before
	public void setUp(){
		System.out.println("====setup data=====");
		event = new TaskEvents();
		event.setId(10000);
		event.setCode("E100000");
		event.setName("test-task-event");
		event.setType(1);
		event.setMsg("test-task-event-msg");
		event.setCreated(new Date());
		event.setUpdated(new Date());
		eventKey = "task:event:"+event.getId();
		System.out.println(eventKey);
	}
	
	@After
	public void clean(){
		System.out.println("====clean all test data=====");
		taskEventRedisCacheManager.deleteValueBykey(eventKey);
	}
	
	@Test
	public void testSetTaskEventsToCache(){
				
		taskEventRedisCacheManager.addValueBykey(eventKey, event);
		
		TaskEvents eventTemp = (TaskEvents) taskEventRedisCacheManager.getValueByKey(eventKey);
		
		assertThat("task event id should be equal",event.getId(), is(eventTemp.getId()));
		
	}
}
