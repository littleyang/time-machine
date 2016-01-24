package com.vanke.status.machine.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.status.machine.dao.TaskStatusDao;
import com.vanke.test.base.BaseTestUnit;

public class TaskStatusDaoUnitTest extends BaseTestUnit {
	
	@Autowired 
	private TaskStatusDao statusDao;
	
	@Test
	public void testGetAllStatusCount(){
		System.out.println(statusDao.getAllStatusCountByJdbc());
	}

}




