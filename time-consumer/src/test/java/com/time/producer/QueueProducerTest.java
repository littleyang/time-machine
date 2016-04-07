package com.time.producer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.common.model.base.BaseObject;
import com.vanke.common.queue.producer.MessageQueueDispatcher;
import com.vanke.test.base.BaseProducerTestUnit;

public class QueueProducerTest extends BaseProducerTestUnit{
	
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
		// 一般开发先注释掉
		messageQueueDispatcher.dispatchToTaskDestination(testObject);
	}

}
