package com.vanke.status.machine.dao.test;

import org.junit.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.status.machine.dao.StatusEventsDao;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskStatusDaoTest extends BaseDaoTestBeans {
	
	@SpringBeanByType  
	private StatusEventsDao eventsDao;
	
	@Test
	@DataSet("/data/task-events-test-data.xml")
	public void testExportData(){
		System.out.println(eventsDao.getAllStatusEventsCount());
	}

}




