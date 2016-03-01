package com.vanke.common.queue.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.common.model.base.BaseObject;
import com.vanke.common.queue.producer.MessageQueueDispatcher;
import com.vanke.test.base.BaseTestUnit;

public class QueueProducerTest extends BaseTestUnit{
	
	@Autowired
	private MessageQueueDispatcher messageQueueDispatcher;
	
	private BaseObject testObject;
	
	@Before
	public void setUp(){
		System.out.println("====setup data=====");
		testObject = new BaseObject();
		testObject.setBaseType("CreateTask");
	}
	
	
	@After
	public void cleanEachTest(){
		System.out.println("====clean data=====");
	}
	
	@Test
	public void createTaskAndPushToTaskQueueTest(){
		messageQueueDispatcher.dispatchToTaskDestination(testObject);
	}

}
