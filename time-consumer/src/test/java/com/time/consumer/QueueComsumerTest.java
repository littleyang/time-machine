package com.time.consumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.common.model.base.BaseObject;
import com.vanke.test.base.BaseConsumerTestUnit;

public class QueueComsumerTest extends BaseConsumerTestUnit{
	
	
	@Autowired
	private TaskComsumerMessageListener taskComsumerMessageListener;

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
		// messageQueueDispatcher.dispatchToTaskDestination(testObject);
	}
	
}
