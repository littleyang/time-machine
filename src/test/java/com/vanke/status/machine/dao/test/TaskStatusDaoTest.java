package com.vanke.status.machine.dao.test;

import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.test.base.BaseDaoTestBeans;

public class TaskStatusDaoTest extends BaseDaoTestBeans {
	
	@SpringBeanByType  
	private TaskStatusDao statusDao;
	
	@Test
	public void testGetAllStatusCount(){
		System.out.println(statusDao.getAllStatusCount());
	}

}




