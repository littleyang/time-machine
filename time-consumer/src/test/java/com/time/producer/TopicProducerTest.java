package com.time.producer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.common.model.base.BaseObject;
import com.vanke.common.topic.producer.MessageTopicDispatcher;
import com.vanke.test.base.BaseProducerTestUnit;

public class TopicProducerTest extends BaseProducerTestUnit{
	
	@Autowired
	private MessageTopicDispatcher messageTopicDispatcher;
	
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
	public void createTaskAndPushToTaskTopicTest(){
		// 一般开发先注释掉
		messageTopicDispatcher.publishToTaskTopicDestination(testObject);
	}
	
	@Test
	public void createTaskTextMessageAndPushToTaskTopicTest(){
		// 一般开发先注释掉
		messageTopicDispatcher.publishTaskTextMessage("this is a test message");
	}

}
