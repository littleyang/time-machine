package com.vanke.status.machine.dao.test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mockito.InjectMocks;

import com.vanke.status.machine.dao.StatusEventsDao;
import com.vanke.test.base.BaseTest;

public class TaskStatusEventsDaoTest extends BaseTest{
	
	@InjectMocks
	private StatusEventsDao eventsDao;
	
	@Test
	public void testGetAllStatusEventsShouldBeZero(){
		assertThat(eventsDao.getAllStatusEventsCount(), is(0));
	}
	
	@Test 
	public void exportData(){
		
	}

}
