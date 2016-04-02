package com.vanke.status.machine.cache.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;

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
	
	@SuppressWarnings("unchecked")
	@After
	public void clean(){
		System.out.println("====clean all test data=====");
		taskEventRedisCacheManager.getRedisTemplate().execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) {
				connection.flushDb();
				return null;
			}
		});
	}
	
	@Test
	public void testSetTaskEventsToCache(){
				
		taskEventRedisCacheManager.addValueBykey(eventKey, event);
		
		TaskEvents eventTemp = (TaskEvents) taskEventRedisCacheManager.getValueByKey(eventKey);
		
		assertThat("task event id should be equal",event.getId(), is(eventTemp.getId()));
		
	}
	
	@Test
	public void testRedisCacheTimeOut() throws InterruptedException{
		long time = 3L;
		TimeUnit unit = null;
		taskEventRedisCacheManager.addBoundValueBykeyAndExpire(eventKey, event, time, unit);
		Thread.sleep(4*1000);
		assertThat("task event id should be equal",null, is(taskEventRedisCacheManager.getValueByKey(eventKey)));
	}
	
	@Test
	public void testRedisTemplateExpireTime() throws InterruptedException{
		long time = 3L;
		TimeUnit unit = TimeUnit.SECONDS;
		taskEventRedisCacheManager.addValueBykeyAndExpire(eventKey, event, time, unit);
		Thread.sleep(4*1000);
		assertThat("task event id should be equal",null, is(taskEventRedisCacheManager.getValueByKey(eventKey)));
	}
}
