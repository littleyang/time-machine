package com.vanke.common.queue.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.common.queue.comsumer.TaskComsumerMessageListener;
import com.vanke.test.base.BaseTestUnit;

public class QueueComsumerTest extends BaseTestUnit{
	
	
	@Autowired
	private TaskComsumerMessageListener taskComsumerMessageListener;

}
