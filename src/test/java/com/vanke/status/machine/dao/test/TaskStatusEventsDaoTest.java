package com.vanke.status.machine.dao.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.vanke.status.machine.dao.StatusEventsDao;
import com.vanke.test.base.BaseTest;

public class TaskStatusEventsDaoTest extends BaseTest{
	
	@Autowired
	private StatusEventsDao eventsDao;
	
	@Test
	public void testGetAllStatusEvents(){
		System.out.println(eventsDao.getAllStatusEvents());
	}

}
