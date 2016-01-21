package com.vanke.status.machine.dao.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.unitils.UnitilsJUnit4;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.status.machine.dao.StatusEventsDao;

public class TaskStatusDaoTest extends UnitilsJUnit4 {
	
	@SpringApplicationContext({ "classpath:application-contex-test.xml"})  
	protected ApplicationContext applicationContext;  
	
	@SpringBeanByType  
	private StatusEventsDao eventsDao;
	
	@Test
	@DataSet("/data/task-events-test-data.xml")
	public void testExportData(){
		System.out.println(eventsDao.getAllStatusEventsCount());
	}

}




