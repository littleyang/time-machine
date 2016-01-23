package com.vanke.status.machine.dao.test;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.status.machine.dao.TaskEventsDao;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskEventsDaoDataTest extends BaseDaoTestBeans{
	
	@SpringBeanByType 
	private TaskEventsDao eventsDao;
	
	@Test
	//@DataSet("/data/task-events-test-data.xml")
	public void testGetAllEventsCount(){
		System.out.println(eventsDao.getAllEventsCount());
	}


}
